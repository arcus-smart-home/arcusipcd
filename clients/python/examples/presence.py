#!/usr/bin/env python3

import logging

from ipcdclient.client import IpcdClient


def main():
  logger = logging.getLogger(__name__)
  logger.setLevel(logging.DEBUG)
  ch = logging.StreamHandler()
  ch.setLevel(logging.DEBUG)
  logger.addHandler(ch)

  client = IpcdClient('wss://arcus.example.com')
  device = IpcdClient.Device('Generic', 'PresenceSensor', '12345678')

  client.add_device(device)

  client.connect()

  logger.info("marking device as present")

  client.on_value_change(device, [{
    'parameter': 'generic.presence', 'value': 'present'
  }])

  client.on_value_change(device, [{
    'parameter': 'generic.presence', 'value': 'absent'
  }])

  client.disconnect()

if __name__ == "__main__":
  main()
