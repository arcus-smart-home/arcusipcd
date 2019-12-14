package com.arcussmarthome.ipcd.client.handler;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.GetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.GetEventConfigurationResponse;
import com.arcussmarthome.ipcd.msg.Status;

public class GetEventConfigurationHandler extends CommandHandler<GetEventConfigurationCommand> {

	
	public GetEventConfigurationHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(GetEventConfigurationCommand command) {
		GetEventConfigurationResponse resp = new GetEventConfigurationResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		resp.setStatus(new Status());
		resp.setResponse(client.getDeviceModel().getGetEventConfiguration());
		
		client.sendMessage(resp, false);
	}
}
