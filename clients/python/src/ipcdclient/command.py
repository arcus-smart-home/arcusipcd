__all__ = ['IpcdCommand', 'from_payload']


class IpcdCommand(object):
  """
  IPCD Command
  """
  def __init__(self):
    self.txnid = ""


class DownloadCommand(IpcdCommand):
  """
  Called when the platform wants the device to update.
  Unsupported!
  """
  def __init__(self):
    super().__init__()

  def apply(self, device):
    pass  # not supported, do nothing.


class FactoryResetCommand(IpcdCommand):
  """
  Called when the platform wants to get information about the device.
  """
  def __init__(self):
    super().__init__()

  def apply(self, device):
    device.on_factory_reset()


class GetDeviceInfoCommand(IpcdCommand):
  """
  Called when the platform wants to get information about the device.
  """
  def __init__(self):
    super().__init__()

  def apply(self, device):
    return {
      'fwver': '1.0',
      'connection': 'on-demand',
      'uptime': device.get_uptime(),
    }


class GetEventConfigurationCommand(IpcdCommand):
  pass


class GetParameterInfoCommand(IpcdCommand):
  pass


class GetParameterValuesCommand(IpcdCommand):
  def __init__(self, txnid, parameters):
    pass


class LeaveCommand(IpcdCommand):
  """
  Called when the platform wants the device to leave.
  Unsupported!
  """
  def __init__(self):
    super().__init__()

  def apply(self, device):
    pass  # not supported, do nothing.


class SetDeviceInfoCommand(IpcdCommand):
  pass


class SetEventConfigurationCommand(IpcdCommand):
  pass


class SetParameterValuesCommand(IpcdCommand):
  def __init__(self, values, txnid):
    super().__init__()
    self.values = values
    self.txnid = txnid

  async def apply(self, device):
    await device.set_values(self.values)


class SetReportConfigurationCommand(IpcdCommand):
  pass


commands = {
  'GetDeviceInfo':         GetDeviceInfoCommand,
  'Download':              DownloadCommand,
  'SetDeviceInfo':         SetDeviceInfoCommand,
  'GetParameterValues':    GetParameterValuesCommand,
  'SetEventConfiguration': SetEventConfigurationCommand,
  'SetParameterValues':    SetParameterValuesCommand,
}


def from_payload(msg):
  """
  Creates a command based on platform-specified input.
  :param msg:
  :return:
  """
  cmd = msg.pop('command')

  # TODO: handle case that command is not valid

  return commands[cmd](**msg)