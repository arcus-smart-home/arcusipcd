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
