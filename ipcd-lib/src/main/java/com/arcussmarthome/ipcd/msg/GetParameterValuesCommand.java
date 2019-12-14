package com.arcussmarthome.ipcd.msg;

import java.util.List;

public class GetParameterValuesCommand extends AbstractCommand {

	private List<String> parameters;

	public GetParameterValuesCommand() {
		super(CommandType.GetParameterValues);
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
		result = prime * result + ((parameters == null) ? 0 : parameters.hashCode());
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
		GetParameterValuesCommand other = (GetParameterValuesCommand) obj;
		if (command != other.command)
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
		return "GetParameterValuesCommand [command=" + command + ", parameters="
				+ parameters + "]";
	}
	
	
	
	
}
