package com.arcussmarthome.ipcd.msg;

public class GetReportConfigurationCommand extends AbstractCommand {


	public GetReportConfigurationCommand() {
		super(CommandType.GetReportConfiguration);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
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
		GetReportConfigurationCommand other = (GetReportConfigurationCommand) obj;
		if (command != other.command)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "GetReportConfigurationCommand [command=" + command + "]";
	}

	
}
