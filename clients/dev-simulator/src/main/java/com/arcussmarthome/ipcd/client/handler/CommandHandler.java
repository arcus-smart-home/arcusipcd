package com.arcussmarthome.ipcd.client.handler;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.Device;
import com.arcussmarthome.ipcd.msg.ServerMessage;

public abstract class CommandHandler<T extends ServerMessage> {

	public T type;
	protected IpcdClientDevice client;
	
	public CommandHandler(IpcdClientDevice client) {
		this.client = client;
	}
	
	public Class<?> getType() {
		return type.getClass();
	}

	protected Device getDevice() {
		Device d = new Device();
		d.setIpcdver(client.getDeviceModel().getIpcdver());
		d.setModel(client.getDeviceModel().getModel());
		d.setSn(client.getDeviceModel().getSn());
		d.setVendor(client.getDeviceModel().getVendor());
		return d;
	}
	
	public abstract void handleCommand(T command);
	
	
}
