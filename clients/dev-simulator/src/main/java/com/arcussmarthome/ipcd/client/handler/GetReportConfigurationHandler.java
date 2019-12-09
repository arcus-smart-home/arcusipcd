package com.arcussmarthome.ipcd.client.handler;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.GetReportConfigurationCommand;
import com.arcussmarthome.ipcd.msg.GetReportConfigurationResponse;
import com.arcussmarthome.ipcd.msg.Status;

public class GetReportConfigurationHandler extends CommandHandler<GetReportConfigurationCommand> {

	
	public GetReportConfigurationHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(GetReportConfigurationCommand command) {
		GetReportConfigurationResponse resp = new GetReportConfigurationResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		resp.setStatus(new Status());
		resp.setResponse(client.getReportConfiguration());
		
		client.sendMessage(resp, false);
	}

	
	
}
