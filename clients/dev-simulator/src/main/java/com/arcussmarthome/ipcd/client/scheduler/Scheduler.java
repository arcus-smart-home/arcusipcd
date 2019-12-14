package com.arcussmarthome.ipcd.client.scheduler;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.Event;

public class Scheduler {

	private static final Scheduler _instance = new Scheduler();
	private ScheduledExecutorService executor = new ScheduledThreadPoolExecutor(2);
	
	
	private Scheduler() {
		// TODO Auto-generated constructor stub
	}
	
	public static Scheduler getInstance() {
		return _instance;
	}
	
	public void scheduleReport(IpcdClientDevice device, int period) {
		ScheduledFuture<?> reportFuture = executor.scheduleAtFixedRate(new ScheduledReportAction(device), 1, period, TimeUnit.SECONDS);
		device.getDeviceModel().setReportFuture(reportFuture);
	}

	public void scheduleReboot(IpcdClientDevice device) {
		scheduleReboot(device, new ArrayList<Event>());
	}
	
	public void scheduleReboot(IpcdClientDevice device, List<Event> events) {
		if (!device.getDeviceModel().isRebooting()) {
			ScheduledFuture<?> rebootFuture = executor.schedule(new ScheduledReboot(device, events), device.getDeviceModel().getSimulatedRebootInterval(), TimeUnit.SECONDS);
		} else {
			// eat request -- can't schedule a new reboot if we're waiting for a reboot already 
			// TODO log message
		}
	}
	
	public void scheduleUpgrade(IpcdClientDevice device, String upgradeURL) {
		if (!device.getDeviceModel().isUpgrading()) {
			ScheduledFuture<?> upgradeFuture = executor.schedule(new ScheduledUpgrade(device, upgradeURL), 2, TimeUnit.SECONDS);
		} else {
			// eat request -- can't schedule a new upgrade if we're waiting for an upgrade to complete
			// TODO log message
		}
	}
}
