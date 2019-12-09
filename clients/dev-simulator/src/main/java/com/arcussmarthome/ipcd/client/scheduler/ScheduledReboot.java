package com.arcussmarthome.ipcd.client.scheduler;

import java.util.ArrayList;
import java.util.List;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.Event;

public class ScheduledReboot implements Runnable {

	private IpcdClientDevice device;
	private List<Event> events;

	public ScheduledReboot(IpcdClientDevice device) {
		this(device, new ArrayList<Event>());
	}
	
	public ScheduledReboot(IpcdClientDevice device, List<Event> events) {
		this.device = device;
		this.events = events;
	}
	
	@Override
	public void run() {
		if (!events.contains(Event.onBoot)) {
			events.add(Event.onBoot);
		}
		if (!events.contains(Event.onConnect)) {
			events.add(Event.onConnect);
		}
		device.doReboot(events);
	}
	
}
