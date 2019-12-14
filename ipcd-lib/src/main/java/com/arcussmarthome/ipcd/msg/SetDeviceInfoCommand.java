package com.arcussmarthome.ipcd.msg;

import java.util.Map;

public class SetDeviceInfoCommand extends AbstractCommand {
	
	private Map<String,Object> values;
	
	public SetDeviceInfoCommand() {
		super(CommandType.SetDeviceInfo);
	}
	
	public Map<String, Object> getValues() {
		return values;
	}
	
	public void setValues(Map<String, Object> values) {
		this.values = values;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result + ((values == null) ? 0 : values.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SetDeviceInfoCommand other = (SetDeviceInfoCommand) obj;
		if (command != other.command)
			return false;
		if (values == null) {
			if (other.values != null)
				return false;
		} else if (!values.equals(other.values))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SetDeviceInfoCommand [command=" + command + ", values="
				+ values + "]";
	}
}
