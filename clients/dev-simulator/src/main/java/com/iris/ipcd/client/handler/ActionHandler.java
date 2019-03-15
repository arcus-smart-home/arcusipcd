package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.msg.ClientMessage;
import com.iris.ipcd.msg.Device;

public abstract class ActionHandler<T extends ClientMessage> {

	public T type;
	protected IpcdClientDevice client;
	
	public ActionHandler(IpcdClientDevice client) {
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
	
	public abstract T generate();
	
	
}
