# Arcus IPCD Client

This project contains an IPCD Client in Python that allows the user to implement a minimal amount of code in order to control devices.  

# Examples

## Report Only

This example shows how to write a client that reports a value change. In this case, you don't need to write a hardware stub, as there's nothing to change on-device:

```python
from ipcdclient.client import IpcdClient

client = IpcdClient('wss://arcus.example.com')
device = IpcdClient.Device('Generic', 'ContactSensor', '123457')
client.add_device(device)

client.connect()

client.on_value_change(device, [{
  'parameter': 'generic.contact', 'value': 'closed'
}])
```

## Bidirectional

```python
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
  client = IpcdClient('wss://arcus.example.com')

  device = FakeGenericDimmer('12345678')
  client.add_device(device)

  client.connect()
```


# Running tests

First export PYTHONPATH so python can find ipcdclient:

`export PYTHONPATH=src`

Then run the tests:

`python3 -m unittest`
