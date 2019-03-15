package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.msg.FactoryResetCommand;
import com.iris.ipcd.msg.FactoryResetResponse;
import com.iris.ipcd.msg.Status;

public class FactoryResetHandler extends CommandHandler<FactoryResetCommand> {

	// for now pass the device model reference in
	public FactoryResetHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(FactoryResetCommand command) {
		FactoryResetResponse resp = new FactoryResetResponse();
		resp.setDevice(getDevice());
		resp.setRequest(command);
		client.doFactoryReset();
		resp.setStatus(new Status());
		// download response is always empty
		// resp.setResponse(response);
		client.sendMessage(resp, false);
	}

}
