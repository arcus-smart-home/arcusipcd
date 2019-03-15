package com.iris.ipcd.client.scheduler;

import com.iris.ipcd.client.comm.IpcdClientDevice;

public class ScheduledReportAction implements Runnable {

	private IpcdClientDevice device;
	
	public ScheduledReportAction(IpcdClientDevice device) {
		this.device = device;
	}

	@Override
	public void run() {
		device.doReport();
	}

}
