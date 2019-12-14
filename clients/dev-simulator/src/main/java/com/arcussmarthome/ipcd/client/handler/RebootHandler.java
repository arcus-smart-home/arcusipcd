package com.arcussmarthome.ipcd.client.handler;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.client.scheduler.Scheduler;
import com.arcussmarthome.ipcd.msg.RebootCommand;
import com.arcussmarthome.ipcd.msg.RebootResponse;
import com.arcussmarthome.ipcd.msg.Status;

public class RebootHandler extends CommandHandler<RebootCommand> {

		// for now pass the device model reference in
		public RebootHandler(IpcdClientDevice client) {
			super(client);
		}

		@Override
		public void handleCommand(RebootCommand command) {
			RebootResponse resp = new RebootResponse();
			resp.setDevice(getDevice());
			resp.setRequest(command);
			
			Scheduler.getInstance().scheduleReboot(client);
			
			resp.setStatus(new Status());
			client.sendMessage(resp, false);
		}



}
