import asyncio
import certifi
import json
import urllib3
import websockets
import logging
import threading
from enum import Enum
from time import time

from .command import from_payload

__all__ = ['IpcdClient']


def _validate_hostname(hostname):
  if not hostname:
    raise ValueError("hostname must be set")


class IpcdClient(object):
  """
  A simple IP Connected Device (IPCD) Client
  https://github.com/arcus-smart-home/arcusipcd
  """
  IPCD_VERSION = '1.0'

  class Device(object):
    """
    Represents a Device.
    """

    def __init__(self, vendor, model, sn, ipcd_version=None):
      self.vendor = vendor
      self.model = model
      self.sn = sn

      if not ipcd_version:
        self.ipcd_version = IpcdClient.IPCD_VERSION
      else:
        self.ipcd_version = ipcd_version

      self._client = None
      self.start = time()

    def to_obj(self):
      data = {
        'ipcdver': self.ipcd_version,
        'vendor':  self.vendor,
        'model':   self.model,
        'sn':      self.sn
      }

      return data

    def _set_client(self, client):
      """
      Used to support Device methods.
      :param client:
      :return:
      """
      self._client = client

    def get_client(self):
      """
      Callers should not attempt to use the client directly. In the future this is likely to be stubbed
      with a "NoopClient" for cases where a client is not available.
      :return:
      """
      return self._client

    async def on_message(self, message):
      """

      :param message:
      :return:
      """
      cmd = from_payload(message)
      await cmd.apply(self)

    def get_uptime(self):
      """
      Gets the device uptime (since it was constructed)
      :return:
      """
      return int(time() - self.start)


  class InternalMessage(object):
    class InternalMessageType(Enum):
      MESSAGE = 1
      DISCONNECT = 2

    def __init__(self, type, payload):
      self.type = type
      self.payload = payload

    def is_type(self, type):
      return self.type is type

    def get_payload(self):
      return self.payload


  def __init__(self, hostname):
    _validate_hostname(hostname)
    self.hostname = hostname
    self.ipcd_version = self.IPCD_VERSION
    self.pool = urllib3.PoolManager(cert_reqs='CERT_REQUIRED', ca_certs=certifi.where())
    self.devices = []
    self.queue = asyncio.Queue()
    self.state = 'DISCONNECTED'
    self._setup_logger()

  def _setup_logger(self):
    self.logger = logging.getLogger(__name__)
    self.logger.setLevel(logging.DEBUG)
    ch = logging.StreamHandler()
    ch.setLevel(logging.DEBUG)
    self.logger.addHandler(ch)

  def get_report_url(self, device):
    """
      Gets the report url. The IPCD spec says this should be
      /ipcd/<ipcdver>/report/<vendor_key>/<model_key>/<sn>

      :param device:
      :return:
      """
    return self.hostname + '/ipcd/{}/report/{}/{}/{}'.format(self.ipcd_version, device.vendor, device.model, device.sn)

  def report(self, device, report):
    """
    Report via on-demand url (currently not supported in Arcus Platform)
    :param device:
    :param report:
    :return:
    """
    url = self.get_report_url(device)
    data = {
      'device': {
        'ipcdver': self.ipcd_version,
        'vendor': device.vendor,
        'model': device.model,
        'sn': device.sn
      },
      'report': report
    }

    request = self.pool.request('POST', url, body=json.dumps(data), headers={
      'Content-Type': 'application/json', 'User-Agent': 'Arcus IPCD Client 1.0'
    })

    return request

  def add_device(self, device):
    device._set_client(self)
    self.devices.append(device)

  def connect(self):
    """
    Connect to the server via websockets
    :return:
    """

    if not self.devices:
      raise ValueError("Can't connect without at least one device added.")

    self.state = 'CONNECTING'

    async def connect_loop():
      async with websockets.connect(self.hostname + '/ipcd/1.0') as websocket:
        loop = asyncio.get_event_loop()

        # First, add all the pre-registered devices.
        for device in self.devices:
          data = {
            'device': {
              'ipcdver': self.ipcd_version,
              'vendor': device.vendor,
              'model': device.model,
              'sn': device.sn
            },
            'events': ['onBoot', 'onConnect']
          }

          await websocket.send(json.dumps(data))
          self.state = 'CONNECTED'

        async def reader(websocket):
          while True:
            msg = await websocket.recv()
            # TODO: determine which device this is for when we support bridges
            loop.create_task(self.devices[0].on_message(json.loads(msg)))

        async def writer(websocket):
          while True:
            msg = await self.queue.get()
            if msg.is_type(self.InternalMessage.InternalMessageType.MESSAGE):
              await websocket.send(msg.payload)
            elif msg.is_type(self.InternalMessage.InternalMessageType.DISCONNECT):
              self.logger.info('disconnecting')
              self.logger.debug('tearing down reader')
              self.reader.cancel()
              await websocket.close()
              self.writer.cancel()
            else:
              self.logger.warn('unhandled message type')
            self.queue.task_done()

        self.reader = loop.create_task(reader(websocket))
        self.writer = loop.create_task(writer(websocket))

        await websocket.wait_closed()

    def loop_in_thread(loop):
      asyncio.set_event_loop(loop)
      loop.run_until_complete(connect_loop())

    self.loop = asyncio.get_event_loop()
    t = threading.Thread(target=loop_in_thread, args=(self.loop,))
    t.start()

  def disconnect(self):
    self.logger.info('Sending disconnect request')
    msg = self.InternalMessage(
      self.InternalMessage.InternalMessageType.DISCONNECT,
      None  # For disconnect
    )

    self.loop.call_soon_threadsafe(self.queue.put_nowait, msg)

  def send(self, device, message):
    """
    Wrapper. Need to rename!
    :param device:
    :param message:
    :return:
    """
    self.on_value_change(device, message)

  def on_value_change(self, device, message):
    """
    Send a device report over IPCD
    :param device:
    :param message:
    :return:
    """
    if not self.is_connected():
      raise ValueError("tried to report a value change without a connection")

    data = {
      'device': {
        'ipcdver': self.ipcd_version,
        'vendor': device.vendor,
        'model': device.model,
        'sn': device.sn,
      },
      'events': ['onValueChanges'],
      'valueChanges': message
    }

    payload = json.dumps(data)
    self.logger.info("putting %s on the queue", payload)
    msg = self.InternalMessage(
      self.InternalMessage.InternalMessageType.MESSAGE,
      payload
    )

    self.loop.call_soon_threadsafe(self.queue.put_nowait, msg)

  def report(self, device, message):
    """
    Send a device report over IPCD
    :param device:
    :param message:
    :return:
    """
    if not self.is_connected():
      raise ValueError("tried to report device status without a connection")

    data = {
      'device': {
        'ipcdver': self.ipcd_version,
        'vendor': device.vendor,
        'model': device.model,
        'sn': device.sn,
      },
      'report': message
    }

    payload = json.dumps(data)
    self.logger.info("report: putting %s on the queue", payload)
    msg = self.InternalMessage(
      self.InternalMessage.InternalMessageType.MESSAGE,
      payload
    )

    self.loop.call_soon_threadsafe(self.queue.put_nowait, msg)

  def is_connected(self):
    return self.state == 'CONNECTED' or self.state == 'CONNECTING'


def main():
  """
  TODO: write a simple app to test from command line.
  :return:
  """


if __name__ == "__main__":
  main()
