package com.arcussmarthome.ipcd.msg;

import java.util.List;

public class EventAction implements Action {

	private Device device;
	private List<Event> events;
	private List<ValueChange> valueChanges;
	
	public Device getDevice() {
		return device;
	}
	public void setDevice(Device device) {
		this.device = device;
	}
	public List<Event> getEvents() {
		return events;
	}
	public void setEvents(List<Event> events) {
		this.events = events;
	}
	public List<ValueChange> getValueChanges() {
		return valueChanges;
	}
	public void setValueChanges(List<ValueChange> valueChanges) {
		this.valueChanges = valueChanges;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((device == null) ? 0 : device.hashCode());
		result = prime * result + ((events == null) ? 0 : events.hashCode());
		result = prime * result
				+ ((valueChanges == null) ? 0 : valueChanges.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EventAction other = (EventAction) obj;
		if (device == null) {
			if (other.device != null)
				return false;
		} else if (!device.equals(other.device))
			return false;
		if (events == null) {
			if (other.events != null)
				return false;
		} else if (!events.equals(other.events))
			return false;
		if (valueChanges == null) {
			if (other.valueChanges != null)
				return false;
		} else if (!valueChanges.equals(other.valueChanges))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EventAction [device=" + device + ", events=" + events
				+ ", valueChanges=" + valueChanges + "]";
	}
	
	
	
	
}
