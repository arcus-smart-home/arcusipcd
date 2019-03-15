package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.client.scheduler.Scheduler;
import com.iris.ipcd.msg.DownloadCommand;
import com.iris.ipcd.msg.DownloadResponse;
import com.iris.ipcd.msg.Status;

public class DownloadHandler extends CommandHandler<DownloadCommand> {

	// for now pass the device model reference in
	public DownloadHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(DownloadCommand command) {
		DownloadResponse resp = new DownloadResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		
		Scheduler.getInstance().scheduleUpgrade(client, command.getUrl());
		
		resp.setStatus(new Status());
		// download response is always empty
		// resp.setResponse(response);
		client.sendMessage(resp, false);
	}

}
