package com.arcussmarthome.ipcd.msg;

public class SetReportConfigurationResponse implements Response<SetReportConfigurationCommand> {

	private Device device;
	private SetReportConfigurationCommand request;
	private Status status;
	private ReportConfiguration response;
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public SetReportConfigurationCommand getRequest() {
		return request;
	}
	public void setRequest(SetReportConfigurationCommand request) {
		this.request = request;
	}
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public ReportConfiguration getResponse() {
		return response;
	}
	public void setResponse(ReportConfiguration response) {
		this.response = response;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((request == null) ? 0 : request.hashCode());
		result = prime * result
				+ ((response == null) ? 0 : response.hashCode());
		result = prime * result + ((status == null) ? 0 : status.hashCode());
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
		SetReportConfigurationResponse other = (SetReportConfigurationResponse) obj;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (request == null) {
			if (other.request != null)
				return false;
		} else if (!request.equals(other.request))
			return false;
		if (response == null) {
			if (other.response != null)
				return false;
		} else if (!response.equals(other.response))
			return false;
		if (status == null) {
			if (other.status != null)
				return false;
		} else if (!status.equals(other.status))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "SetReportConfigurationResponse [device=" + device
				+ ", request=" + request + ", status=" + status + ", response="
				+ response + "]";
	}

}
