package com.arcussmarthome.ipcd.msg;

import java.util.List;
import java.util.Map;

public class GetEventConfiguration {

	private List<Event> supportedEvents;
	private List<Event> enabledEvents;
	private Map<String,List<ThresholdRule>> supportedValueChanges;
	private Map<String,ValueChangeThreshold> enabledValueChanges;
	
	public List<Event> getSupportedEvents() {
		return supportedEvents;
	}
	public void setSupportedEvents(List<Event> supportedEvents) {
		this.supportedEvents = supportedEvents;
	}
	public List<Event> getEnabledEvents() {
		return enabledEvents;
	}
	public void setEnabledEvents(List<Event> enabledEvents) {
		this.enabledEvents = enabledEvents;
	}
	public Map<String, List<ThresholdRule>> getSupportedValueChanges() {
		return supportedValueChanges;
	}
	public void setSupportedValueChanges(Map<String, List<ThresholdRule>> supportedValueChanges) {
		this.supportedValueChanges = supportedValueChanges;
	}
	public Map<String, ValueChangeThreshold> getEnabledValueChanges() {
		return enabledValueChanges;
	}
	public void setEnabledValueChanges(Map<String, ValueChangeThreshold> enabledValueChanges) {
		this.enabledValueChanges = enabledValueChanges;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((enabledEvents == null) ? 0 : enabledEvents.hashCode());
		result = prime
				* result
				+ ((enabledValueChanges == null) ? 0 : enabledValueChanges
						.hashCode());
		result = prime
				* result
				+ ((supportedValueChanges == null) ? 0 : supportedValueChanges
						.hashCode());
		result = prime * result
				+ ((supportedEvents == null) ? 0 : supportedEvents.hashCode());
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
		GetEventConfiguration other = (GetEventConfiguration) obj;
		if (enabledEvents == null) {
			if (other.enabledEvents != null)
				return false;
		} else if (!enabledEvents.equals(other.enabledEvents))
			return false;
		if (enabledValueChanges == null) {
			if (other.enabledValueChanges != null)
				return false;
		} else if (!enabledValueChanges.equals(other.enabledValueChanges))
			return false;
		if (supportedValueChanges == null) {
			if (other.supportedValueChanges != null)
				return false;
		} else if (!supportedValueChanges.equals(other.supportedValueChanges))
			return false;
		if (supportedEvents == null) {
			if (other.supportedEvents != null)
				return false;
		} else if (!supportedEvents.equals(other.supportedEvents))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "EventConfiguration [supportedEvents=" + supportedEvents
				+ ", enabledEvents=" + enabledEvents
				+ ", supportedValueChanges=" + supportedValueChanges
				+ ", enabledValueChanges=" + enabledValueChanges + "]";
	}
	
	
}
