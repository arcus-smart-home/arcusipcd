package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.client.scheduler.Scheduler;
import com.iris.ipcd.msg.RebootCommand;
import com.iris.ipcd.msg.RebootResponse;
import com.iris.ipcd.msg.Status;

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
