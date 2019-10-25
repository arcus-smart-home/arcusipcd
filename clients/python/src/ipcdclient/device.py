
__all__ = ['GenericDimmer']

from .client import IpcdClient


class GenericDimmer(IpcdClient.Device):
  def __init__(self, serial):
    self.level = 0
    self.on = False
    super(GenericDimmer, self).__init__('Generic', 'Dimmer', serial)

  def set_level(self, level):
    """
    Set the level on the dimmer. Must be between 0-100 inclusive.
    :param level:
    :return:
    """
    if level == self.level:
      return

    if level < 0 or level > 100:
      raise ValueError()

    self.level = int(level)
    self.get_client().send(self, [{'parameter': 'generic.brightness', 'value': 5}])

  def report(self):
    if self.on == False:
      self.on = True
      self.get_client().send(self, [{'parameter': 'generic.switch', 'value': 'on'}])

  def report(self):
    if self.on == True:
      self.on = False
      self.get_client().send(self, [{'parameter': 'generic.switch', 'value': 'off'}])

  def set_values(self, values):
    """
    Called when the user changes something in Arcus
    :param values:
    :return:
    """
    if 'generic.switch' in values:
      self.turn_on(values['generic.switch'] == 'on')
    if 'generic.brightness' in values:
      self.set_level(values['generic.brightness'])