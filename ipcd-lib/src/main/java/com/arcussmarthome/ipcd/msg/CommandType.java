package com.arcussmarthome.ipcd.msg;

public enum CommandType {
	GetDeviceInfo, SetDeviceInfo, GetParameterValues, SetParameterValues,
	GetParameterInfo, GetReportConfiguration, SetReportConfiguration,
	GetEventConfiguration, SetEventConfiguration, Download, Reboot, FactoryReset,
	Leave	// , Upload 
}
