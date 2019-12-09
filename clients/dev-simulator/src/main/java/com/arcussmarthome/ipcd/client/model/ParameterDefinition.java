package com.arcussmarthome.ipcd.client.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.arcussmarthome.ipcd.msg.ParameterAttrib;
import com.arcussmarthome.ipcd.msg.ParameterInfo;
import com.arcussmarthome.ipcd.msg.ThresholdRule;
import com.arcussmarthome.ipcd.msg.ValueChangeThreshold;

public class ParameterDefinition {

	private String name;
	private String type;
	private List<String> enumvalues;
	private ParameterAttrib attrib;
	private String unit;
	private Double floor;
	private Double ceiling;
	private String description;
	private Object currentValue;
	private transient Object lastValueChangeValue;
	private ParameterValidator validator;
	
	private List<ThresholdRule> supportedValueChanges = new ArrayList<ThresholdRule>();
	private ValueChangeThreshold enabledValueChanges = null;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Object getCurrentValue(boolean returnWriteOnlyAsNull) {
		if (returnWriteOnlyAsNull && this.attrib == ParameterAttrib.writeonly) {
			return null;
		}
		return this.currentValue;
	}

	public void setCurrentValue(Object value) throws ValidationException {
		// TODO  additional type checking on value?
		if (validator != null) {
			validator.validate(value);
		}
		if (floor != null && value instanceof Number && Double.valueOf(value.toString()) < floor) {
			throw new ValidationException(value + " is less than the floor value of " + floor + " for " + name);
		}
		if (ceiling != null && value instanceof Number && Double.valueOf(value.toString()) > ceiling) {
			throw new ValidationException(value + " is greater than the ceiling value of " + ceiling + " for " + name);
		}		
		currentValue = value;
		
	}
	
	public ParameterValidator getValidator() {
		return this.validator;
	}
	
	public void setParameterValidator(ParameterValidator validator) {
		this.validator = validator;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getEnumvalues() {
		return enumvalues;
	}

	public void setEnumvalues(List<String> enumvalues) {
		this.enumvalues = enumvalues;
	}

	public ParameterAttrib getAttrib() {
		return attrib;
	}

	public void setAttrib(ParameterAttrib attrib) {
		this.attrib = attrib;
	}

	public String getUnit() {
		return unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public Double getFloor() {
		return floor;
	}

	public void setFloor(Double floor) {
		this.floor = floor;
	}

	public Double getCeiling() {
		return ceiling;
	}

	public void setCeiling(Double ceiling) {
		this.ceiling = ceiling;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<ThresholdRule> getSupportedValueChanges() {
		return supportedValueChanges;
	}

	public void setSupportedValueChanges(List<ThresholdRule> supportedValueChanges) {
		this.supportedValueChanges = supportedValueChanges;
	}
	
	public void clearSupportedValueChanges() {
		this.supportedValueChanges.clear();
	}
	
	public void clearEnabledValueChanges() {
		this.enabledValueChanges.clear();
	}

	public ValueChangeThreshold getEnabledValueChanges() {
		return enabledValueChanges;
	}
	
	public Object getLastValueChangeValue() {
		return lastValueChangeValue;
	}

	public void setLastValueChangeValue(Object lastValueChangeValue) {
		this.lastValueChangeValue = lastValueChangeValue;
	}

	public void setEnabledValueChanges(ValueChangeThreshold enabledValueChanges) throws ValidationException {
		if (enabledValueChanges.isOnChange() && !supportedValueChanges.contains(ThresholdRule.onChange))
			throw new ValidationException("onChange thresholds are not supported by " + name);
		if (enabledValueChanges.getOnChangeBy() != null && !supportedValueChanges.contains(ThresholdRule.onChangeBy))
			throw new ValidationException("onChangeBy thresholds are not supported by " + name);
		if (enabledValueChanges.getOnEquals() != null && !supportedValueChanges.contains(ThresholdRule.onEquals))
			throw new ValidationException("onEquals thresholds are not supported by " + name);
		if (enabledValueChanges.getOnGreaterThan() != null && !supportedValueChanges.contains(ThresholdRule.onGreaterThan))
			throw new ValidationException("onGreaterThan thresholds are not supported by " + name);
		if (enabledValueChanges.getOnLessThan() != null && !supportedValueChanges.contains(ThresholdRule.onLessThan))
			throw new ValidationException("getOnLessThan thresholds are not supported by " + name);
		this.enabledValueChanges = enabledValueChanges;
	}

	public ParameterInfo getParameterInfo() {
		ParameterInfo pi = new ParameterInfo();
		pi.setType(type);
		if (enumvalues != null) {
			pi.setEnumvalues(Collections.unmodifiableList(enumvalues));
		}
		pi.setAttrib(attrib);
		pi.setUnit(unit);
		pi.setFloor(floor);
		pi.setCeiling(ceiling);
		pi.setDescription(description);
		return pi;
	}
	
	
}
