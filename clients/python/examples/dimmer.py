#!/usr/bin/env python3

import logging
import asyncio

from ipcdclient.client import IpcdClient
from ipcdclient.device import GenericDimmer


class FakeGenericDimmer(GenericDimmer):
  """
  A simple fake dimmer. You should copy this example and replace the stubs with something that actually does something.
  You should only call the report_* methods after the hardware reflects the requested state.
  """
  async def set_level(self, level):
    await asyncio.sleep(2)
    self.report_level(level)

  async def turn_off(self):
    await asyncio.sleep(2)
    self.report_off()

  async def turn_on(self):
    await asyncio.sleep(2)
    self.report_on()


def main():
  logger = logging.getLogger(__name__)
  logger.setLevel(logging.DEBUG)
  ch = logging.StreamHandler()
  ch.setLevel(logging.DEBUG)
  logger.addHandler(ch)

  client = IpcdClient('wss://arcus.example.com')

  device = FakeGenericDimmer('12345678')
  client.add_device(device)

  client.connect()

  logger.info("changing dimmer values")

  # Example with a client
  client.on_value_change(device, [{
    'parameter': 'generic.brightness', 'value': 5,
    'parameter': 'generic.switch', 'value': 'on'
  }])

  client.on_value_change(device, [{
    'parameter': 'generic.brightness', 'value': 50
  }])

  # The device knows which client is using it, and can also automatically communicate.
  device.report_level(50)

  device.report_on()

  client.disconnect()

if __name__ == "__main__":
  main()
