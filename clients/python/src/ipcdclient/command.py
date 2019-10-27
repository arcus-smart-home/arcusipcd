__all__ = ['IpcdCommand', 'from_payload']


class IpcdCommand(object):
  """
  IPCD Command
  """
  def __init__(self):
    self.txnid = ""


class DownloadCommand(IpcdCommand):
  pass


class FactoryResetCommand(IpcdCommand):
  pass


class GetDeviceInfoCommand(IpcdCommand):
  pass


class GetEventConfigurationCommand(IpcdCommand):
  pass


class GetParameterInfoCommand(IpcdCommand):
  pass


class GetParameterValuesCommand(IpcdCommand):
  def __init__(self, txnid, parameters):
    pass


class LeaveCommand(IpcdCommand):
  pass


class SetDeviceInfoCommand(IpcdCommand):
  pass


class SetEventConfigurationCommand(IpcdCommand):
  pass


class SetParameterValuesCommand(IpcdCommand):
  def __init__(self, values, txnid):
    super().__init__()
    self.values = values
    self.txnid = txnid

  def apply(self, device):
    device.set_values(self.values)


class SetReportConfigurationCommand(IpcdCommand):
  pass


commands = {
  'GetParameterValues': GetParameterValuesCommand,
  'SetParameterValues': SetParameterValuesCommand,

}


def from_payload(msg):
  cmd = msg.pop('command')

  return commands[cmd](**msg)