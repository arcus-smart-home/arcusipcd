package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.msg.GetParameterInfoCommand;
import com.iris.ipcd.msg.GetParameterInfoResponse;
import com.iris.ipcd.msg.Status;

public class GetParameterInfoHandler extends CommandHandler<GetParameterInfoCommand> {

	public GetParameterInfoHandler (IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(GetParameterInfoCommand command) {
		GetParameterInfoResponse resp = new GetParameterInfoResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		resp.setStatus(new Status());
		resp.setResponse(client.getDeviceModel().getParameterInfos());
		
		client.sendMessage(resp, false);
	}
}
