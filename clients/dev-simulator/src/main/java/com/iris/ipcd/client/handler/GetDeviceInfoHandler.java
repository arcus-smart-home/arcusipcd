package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.msg.DeviceInfo;
import com.iris.ipcd.msg.GetDeviceInfoCommand;
import com.iris.ipcd.msg.GetDeviceInfoResponse;
import com.iris.ipcd.msg.Status;

public class GetDeviceInfoHandler extends CommandHandler<GetDeviceInfoCommand> {

	// for now pass the device model reference in
	public GetDeviceInfoHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(GetDeviceInfoCommand command) {
		GetDeviceInfoResponse resp = new GetDeviceInfoResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		resp.setStatus(new Status());
		
		DeviceInfo deviceInfo = new DeviceInfo();
		deviceInfo.setUptime(client.getUptime());
		deviceInfo.setConnectUrl(client.getDeviceModel().getConnectUrl());
		deviceInfo.setConnection(client.getDeviceModel().getConnectionType());
		deviceInfo.setFwver(client.getDeviceModel().getFwver());
		deviceInfo.setCommands(client.getDeviceModel().getSupportedCommands());
		deviceInfo.setActions(client.getDeviceModel().getSupportedActions());
		
		resp.setResponse(deviceInfo);
		
		client.sendMessage(resp, false);
	}

}
