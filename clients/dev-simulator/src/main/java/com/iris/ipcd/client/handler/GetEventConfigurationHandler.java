package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.msg.GetEventConfigurationCommand;
import com.iris.ipcd.msg.GetEventConfigurationResponse;
import com.iris.ipcd.msg.Status;

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
