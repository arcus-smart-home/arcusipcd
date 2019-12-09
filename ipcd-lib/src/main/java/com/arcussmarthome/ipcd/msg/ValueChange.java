package com.arcussmarthome.ipcd.msg;

public class ValueChange {

	private String parameter;
	private Object value;
	private ThresholdRule thresholdRule;
	private Object thresholdValue;
	
	public ValueChange() {
		
	}
	
	public ValueChange(String parameter, Object value, ThresholdRule thresholdRule, Object thresholdValue) {
		this.parameter = parameter;
		this.value = value;
		this.thresholdRule = thresholdRule;
		this.thresholdValue = thresholdValue;
	}
	
	public String getParameter() {
		return parameter;
	}
	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
	public Object getValue() {
		return value;
	}
	public void setValue(Object value) {
		this.value = value;
	}
	public ThresholdRule getThresholdRule() {
		return thresholdRule;
	}
	public void setThresholdrule(ThresholdRule thresholdRule) {
		this.thresholdRule = thresholdRule;
	}
	public Object getThresholdValue() {
		return thresholdValue;
	}
	public void setThresholdValue(Object thresholdValue) {
		this.thresholdValue = thresholdValue;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((parameter == null) ? 0 : parameter.hashCode());
		result = prime * result
				+ ((thresholdRule == null) ? 0 : thresholdRule.hashCode());
		result = prime * result
				+ ((thresholdValue == null) ? 0 : thresholdValue.hashCode());
		result = prime * result + ((value == null) ? 0 : value.hashCode());
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
		ValueChange other = (ValueChange) obj;
		if (parameter == null) {
			if (other.parameter != null)
				return false;
		} else if (!parameter.equals(other.parameter))
			return false;
		if (thresholdRule != other.thresholdRule)
			return false;
		if (thresholdValue == null) {
			if (other.thresholdValue != null)
				return false;
		} else if (!thresholdValue.equals(other.thresholdValue))
			return false;
		if (value == null) {
			if (other.value != null)
				return false;
		} else if (!value.equals(other.value))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ValueChange [parameter=" + parameter + ", value=" + value
				+ ", thresholdRule=" + thresholdRule + ", thresholdValue="
				+ thresholdValue + "]";
	}
	
}
