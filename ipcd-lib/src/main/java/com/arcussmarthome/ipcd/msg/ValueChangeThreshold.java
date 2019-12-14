package com.arcussmarthome.ipcd.msg;

import java.util.List;

public class ValueChangeThreshold {

	private boolean onChange;
	private Double onChangeBy;
	private List<Object> onEquals;
	private Double onLessThan;
	private Double onGreaterThan;
	
	public boolean isOnChange() {
		return onChange;
	}
	public void setOnChange(boolean onChange) {
		this.onChange = onChange;
	}
	public Double getOnChangeBy() {
		return onChangeBy;
	}
	public void setOnChangeBy(Double onChangeBy) {
		this.onChangeBy = onChangeBy;
	}
	public List<Object> getOnEquals() {
		return onEquals;
	}
	public void setOnEquals(List<Object> onEquals) {
		this.onEquals = onEquals;
	}
	public Double getOnLessThan() {
		return onLessThan;
	}
	public void setOnLessThan(Double onLessThan) {
		this.onLessThan = onLessThan;
	}
	public Double getOnGreaterThan() {
		return onGreaterThan;
	}
	public void setOnGreaterThan(Double onGreaterThan) {
		this.onGreaterThan = onGreaterThan;
	}
	
	public void clear() {
		this.onChange = false;
		this.onChangeBy = null;
		this.onEquals = null;
		this.onGreaterThan = null;
		this.onLessThan = null;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (onChange ? 1231 : 1237);
		result = prime * result
				+ ((onChangeBy == null) ? 0 : onChangeBy.hashCode());
		result = prime * result
				+ ((onEquals == null) ? 0 : onEquals.hashCode());
		result = prime * result
				+ ((onGreaterThan == null) ? 0 : onGreaterThan.hashCode());
		result = prime * result
				+ ((onLessThan == null) ? 0 : onLessThan.hashCode());
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
		ValueChangeThreshold other = (ValueChangeThreshold) obj;
		if (onChange != other.onChange)
			return false;
		if (onChangeBy == null) {
			if (other.onChangeBy != null)
				return false;
		} else if (!onChangeBy.equals(other.onChangeBy))
			return false;
		if (onEquals == null) {
			if (other.onEquals != null)
				return false;
		} else if (!onEquals.equals(other.onEquals))
			return false;
		if (onGreaterThan == null) {
			if (other.onGreaterThan != null)
				return false;
		} else if (!onGreaterThan.equals(other.onGreaterThan))
			return false;
		if (onLessThan == null) {
			if (other.onLessThan != null)
				return false;
		} else if (!onLessThan.equals(other.onLessThan))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "ValueChangeThreshold [onChange=" + onChange + ", onChangeBy="
				+ onChangeBy + ", onEquals=" + onEquals + ", onLessThan="
				+ onLessThan + ", onGreaterThan=" + onGreaterThan + "]";
	}

}
