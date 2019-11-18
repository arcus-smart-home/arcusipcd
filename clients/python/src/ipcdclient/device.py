
__all__ = ['GenericDimmer']

from .client import IpcdClient


class GenericDimmer(IpcdClient.Device):
  """
  Implements a generic dimmer.
  """
  def __init__(self, serial):
    self.level = 0
    self.on = False
    super(GenericDimmer, self).__init__('Generic', 'Dimmer', serial)

  # BEGIN: hw implements
  async def set_level(self, level):
    """
    Implement to set the level of brightness.
    :param level:
    :return:
    """
    raise ValueError("implementation for set_level not implemented")

  async def turn_on(self):
    """
    Implement to turn on the device.
    :return:
    """
    raise ValueError("implementation for turn_on not implemented")

  async def turn_off(self):
    """
    Implement to turn off device
    :return:
    """
    raise ValueError("implementation for turn_off not implemented")

  # END: hw implements

  def report_level(self, level):
    """
    Set the level on the dimmer. Must be between 0-100 inclusive.
    :param level:
    :return:
    """
    self.level = self.validate_level(level)
    self.get_client().on_value_change(self, [{'parameter': 'generic.brightness', 'value': self.level}])

  def report_on(self):
    """
    Call when the device has reported that is on.
    :return:
    """
    if self.on == False:
      self.on = True
      self.get_client().on_value_change(self, [{'parameter': 'generic.switch', 'value': 'on'}])

  def report_off(self):
    """
    Call when the device has reported that is off.
    :return:
    """
    if self.on == True:
      self.on = False
      self.get_client().on_value_change(self, [{'parameter': 'generic.switch', 'value': 'off'}])

  def validate_level(self, level):
    """
    Validate that the level is valid.
    :param level:
    :return:
    """
    if level < 0 or level > 100:
      raise ValueError()

    return int(level)

  async def set_values(self, values):
    """
    Called when the user changes something in Arcus
    :param values:
    :return:
    """
    if 'generic.switch' in values:
      if values['generic.switch'] == 'on':
        await self.turn_on()
      elif values['generic.switch'] == 'off':
        await self.turn_off()
    if 'generic.brightness' in values:
      self.set_level(values['generic.brightness'])


class GenericSwitch(IpcdClient.Device):
  """
  Implements a generic switch.
  """
  def __init__(self, serial):
    self.on = False
    super(GenericSwitch, self).__init__('Generic', 'Switch', serial)

  # BEGIN: hw implements
  async def turn_on(self):
    """
    Implement to turn on the device.
    :return:
    """
    raise ValueError("implementation for turn_on not implemented")

  async def turn_off(self):
    """
    Implement to turn off device
    :return:
    """
    raise ValueError("implementation for turn_off not implemented")

  # END: hw implements

  def report_on(self):
    """
    Call when the device has reported that is on.
    :return:
    """
    if self.on == False:
      self.on = True
      self.get_client().on_value_change(self, [{'parameter': 'generic.switch', 'value': 'on'}])

  def report_off(self):
    """
    Call when the device has reported that is off.
    :return:
    """
    if self.on == True:
      self.on = False
      self.get_client().on_value_change(self, [{'parameter': 'generic.switch', 'value': 'off'}])

  async def set_values(self, values):
    """
    Called when the user changes something in Arcus
    :param values:
    :return:
    """
    if 'generic.switch' in values:
      if values['generic.switch'] == 'on':
        await self.turn_on()
      elif values['generic.switch'] == 'off':
        await self.turn_off()
