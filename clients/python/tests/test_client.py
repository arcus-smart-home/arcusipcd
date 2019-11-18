import unittest

from ipcdclient import IpcdClient


class TestClient(unittest.TestCase):
  def test_device(self):
    device = IpcdClient.Device("Ab", "Cd", "12345")

    self.assertEqual(device.vendor, "Ab")

    self.assertEqual(device.sn, "12345")

  def test_device_to_obj(self):
    device = IpcdClient.Device("Ab", "Cd", "12345")

    self.assertEqual(device.vendor, "Ab")

    self.assertEqual(device.to_obj(), {'ipcdver': '1.0', 'vendor': 'Ab', 'model': 'Cd', 'sn': '12345'})

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

  def test_uptime(self):
    device = IpcdClient.Device("Ab", "Cd", "Ef", "12345")
    import time
    time.sleep(2)
    self.assertGreater(device.get_uptime(), 1)