import unittest

from ipcdclient import IpcdClient


class TestClient(unittest.TestCase):
  def test_device(self):
    device = IpcdClient.Device("Ab", "Cd", "Ef", "12345")

    self.assertEqual(device.vendor, "Ab")

    self.assertEqual(device.sn, "Ef")

  def test_client_hostname(self):
    with self.assertRaises(ValueError):
      client = IpcdClient(None)

    self.assertIsNotNone(IpcdClient("wss://example.com"))

  def test_client_no_device(self):
    client = IpcdClient("wss://arcus.example.com")
    with self.assertRaises(ValueError):
      client.connect()

  def test_send_without_connection(self):
    client = IpcdClient("wss://arcus.example.com")
    device = IpcdClient.Device("Ab", "Cd", "Ef", "12345")
    with self.assertRaises(ValueError):
      client.on_value_change(device, {})

  def test_report_without_connection(self):
    client = IpcdClient("wss://arcus.example.com")
    device = IpcdClient.Device("Ab", "Cd", "Ef", "12345")
    with self.assertRaises(ValueError):
      client.report(device, {})