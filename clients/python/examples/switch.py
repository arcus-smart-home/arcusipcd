#!/usr/bin/env python3

import urllib3
import asyncio
import logging

from ipcdclient.client import IpcdClient
from ipcdclient.device import GenericSwitch


class FakeGenericSwitch(GenericSwitch):
  """
  A simple fake switch. You should copy this example and replace the stubs with something that actually does something.
  You should only call the report_* methods after the hardware reflects the requested state.
  """
  async def turn_off(self):
    print("got a request to turn off. let's pretend this takes two seconds IRL")
    await asyncio.sleep(2)

    print("reporting off")
    self.report_off()

  async def turn_on(self):
    print("got a request to turn on. let's pretend this takes two seconds IRL")
    await asyncio.sleep(2)
    print("reporting on!")
    self.report_on()


def main():
  logger = logging.getLogger(__name__)
  logger.setLevel(logging.DEBUG)
  ch = logging.StreamHandler()
  ch.setLevel(logging.DEBUG)
  logger.addHandler(ch)

  client = IpcdClient('wss://arcus.example.com')

  device = FakeGenericSwitch('87654321')
  client.add_device(device)

  client.connect()

  # Example with a client
  client.on_value_change(device, [{
    'parameter': 'generic.switch', 'value': 'on'
  }])

  # Simulate a user turning the switch on and off every few seconds
  async def run_forever():
    while True:
      await device.turn_on()

      await asyncio.sleep(4)

      await device.turn_off()

  asyncio.run(run_forever())

if __name__ == "__main__":
  main()
