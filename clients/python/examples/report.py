#!/usr/bin/env python3

import logging

from ipcdclient.client import IpcdClient


def main():
  """
  Sets a contact
  :return:
  """
  logger = logging.getLogger(__name__)
  logger.setLevel(logging.DEBUG)
  ch = logging.StreamHandler()
  ch.setLevel(logging.DEBUG)
  logger.addHandler(ch)

  client = IpcdClient('wss://arcus.example.com')
  device = IpcdClient.Device('Generic', 'ContactSensor', '123457')
  client.add_device(device)

  logger.info("marking contact as closed")

  ipcdclient.report(device, {"generic.contact": "closed"})


if __name__ == "__main__":
  main()
