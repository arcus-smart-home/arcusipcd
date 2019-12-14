package com.arcussmarthome.ipcd.client.handler;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.client.scheduler.Scheduler;
import com.arcussmarthome.ipcd.msg.DownloadCommand;
import com.arcussmarthome.ipcd.msg.DownloadResponse;
import com.arcussmarthome.ipcd.msg.Status;

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
