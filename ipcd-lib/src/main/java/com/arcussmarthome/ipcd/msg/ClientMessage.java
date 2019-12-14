package com.arcussmarthome.ipcd.msg;

public interface ClientMessage extends Message {

	public Device getDevice();
	public void setDevice(Device device);
	
}
