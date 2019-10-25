#!/usr/bin/env python3

import logging

from ipcdclient.client import IpcdClient
from ipcdclient.device import GenericDimmer


def main():
  logger = logging.getLogger(__name__)
  logger.setLevel(logging.DEBUG)
  ch = logging.StreamHandler()
  ch.setLevel(logging.DEBUG)
  logger.addHandler(ch)

  client = IpcdClient('wss://arcus.example.com')

  device = GenericDimmer('12345678')
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
  device.set_level(50)

  device.turn_off()

  client.disconnect()

if __name__ == "__main__":
  main()
