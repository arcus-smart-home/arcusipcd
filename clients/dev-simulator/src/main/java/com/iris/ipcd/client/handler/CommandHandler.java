package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.msg.Device;
import com.iris.ipcd.msg.ServerMessage;

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
