# Arcus IPCD Client

# Example

```python
client = IpcdClient('wss://arcus.example.com')
device = IpcdClient.Device('Generic', 'ContactSensor', '123457')
client.add_device(device)

client.connect()

client.send(device, [{
  'parameter': 'generic.contact', 'value': 'closed'
}])
```


# Running tests

First export PYTHONPATH so python can find ipcdclient:

`export PYTHONPATH=src`

Then run the tests:

`python3 -m unittest`
