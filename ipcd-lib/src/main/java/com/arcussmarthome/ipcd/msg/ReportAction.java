package com.arcussmarthome.ipcd.msg;

import java.util.Map;

public class ReportAction implements Action {

	private Device device;
	private Map<String,Object> report;
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public Map<String, Object> getReport() {
		return report;
	}
	public void setReport(Map<String, Object> report) {
		this.report = report;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((report == null) ? 0 : report.hashCode());
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
		ReportAction other = (ReportAction) obj;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (report == null) {
			if (other.report != null)
				return false;
		} else if (!report.equals(other.report))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ReportAction [device=" + device + ", report=" + report + "]";
	}
	
	
	
	
}
