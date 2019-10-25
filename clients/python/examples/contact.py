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
  device = IpcdClient.Device('Generic', 'ContactSensor', '123457')
  client.add_device(device)

  client.connect()

  logger.info("marking contact as closed")

  client.on_value_change(device, [{
    'parameter': 'generic.contact', 'value': 'closed'
  }])

  client.on_value_change(device, [{
    'parameter': 'generic.contact', 'value': 'opened'
  }])


  client.report(device, {"generic.contact": "closed"})
  client.disconnect()

if __name__ == "__main__":
  main()
