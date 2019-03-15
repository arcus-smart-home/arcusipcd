package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.msg.LeaveCommand;
import com.iris.ipcd.msg.LeaveResponse;
import com.iris.ipcd.msg.Status;

public class LeaveHandler extends CommandHandler<LeaveCommand> {

	// for now pass the device model reference in
	public LeaveHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(LeaveCommand command) {
		LeaveResponse resp = new LeaveResponse();
		resp.setDevice(getDevice());
		resp.setRequest(command);
		resp.setStatus(new Status());
		client.sendMessage(resp, false);
		client.disconnect();
	}

}
