package com.arcussmarthome.ipcd.msg;

import java.util.List;

public class SetReportConfigurationCommand extends AbstractCommand {

	private Integer interval;
	private List<String> parameters;
	
	public SetReportConfigurationCommand() {
		super(CommandType.SetReportConfiguration);
	}
	
	public Integer getInterval() {
		return interval;
	}
	public void setInterval(Integer interval) {
		this.interval = interval;
	}
	public List<String> getParameters() {
		return parameters;
	}
	public void setParameters(List<String> parameters) {
		this.parameters = parameters;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((command == null) ? 0 : command.hashCode());
		result = prime * result
				+ ((interval == null) ? 0 : interval.hashCode());
		result = prime * result
				+ ((parameters == null) ? 0 : parameters.hashCode());
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
		SetReportConfigurationCommand other = (SetReportConfigurationCommand) obj;
		if (command != other.command)
			return false;
		if (interval == null) {
			if (other.interval != null)
				return false;
		} else if (!interval.equals(other.interval))
			return false;
		if (parameters == null) {
			if (other.parameters != null)
				return false;
		} else if (!parameters.equals(other.parameters))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SetReportConfigurationCommand [command=" + command
				+ ", interval=" + interval + ", parameters=" + parameters + "]";
	}
	
}
