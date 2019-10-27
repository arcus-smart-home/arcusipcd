#!/usr/bin/env python3

import logging
import time

from ipcdclient.client import IpcdClient
from ipcdclient.device import GenericSwitch


class FakeGenericSwitch(GenericSwitch):
  def turn_off(self):
    time.sleep(2)
    self.report_off()

  def turn_on(self):
    time.sleep(2)
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

  # The device knows which client is using it, and can also automatically communicate.

  while True:
    device.turn_on()

    time.sleep(4)

    device.turn_off()


if __name__ == "__main__":
  main()
