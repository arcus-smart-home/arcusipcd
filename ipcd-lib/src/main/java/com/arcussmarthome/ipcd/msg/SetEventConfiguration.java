package com.arcussmarthome.ipcd.msg;

import java.util.List;
import java.util.Map;

public class SetEventConfiguration {

	private List<Event> enabledEvents;
	private Map<String,ValueChangeThreshold> enabledValueChanges;
	
	public List<Event> getEnabledEvents() {
		return enabledEvents;
	}
	public void setEnabledEvents(List<Event> enabledEvents) {
		this.enabledEvents = enabledEvents;
	}
	public Map<String, ValueChangeThreshold> getEnabledValueChanges() {
		return enabledValueChanges;
	}
	public void setEnabledValueChanges(
			Map<String, ValueChangeThreshold> enabledValueChanges) {
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
		SetEventConfiguration other = (SetEventConfiguration) obj;
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
		return true;
	}
	
	@Override
	public String toString() {
		return "SetEventConfiguration [enabledEvents=" + enabledEvents
				+ ", enabledValueChanges=" + enabledValueChanges + "]";
	}
	
	
}
