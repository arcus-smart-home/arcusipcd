package com.arcussmarthome.ipcd.msg;

import java.util.List;

public class ParameterInfo {

	private String type;
	private List<String> enumvalues;
	private ParameterAttrib attrib;
	private String unit;
	private Double floor;
	private Double ceiling;
	private String description;
	
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
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((attrib == null) ? 0 : attrib.hashCode());
		result = prime * result + ((ceiling == null) ? 0 : ceiling.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result
				+ ((enumvalues == null) ? 0 : enumvalues.hashCode());
		result = prime * result + ((floor == null) ? 0 : floor.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		result = prime * result + ((unit == null) ? 0 : unit.hashCode());
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
		ParameterInfo other = (ParameterInfo) obj;
		if (attrib != other.attrib)
			return false;
		if (ceiling == null) {
			if (other.ceiling != null)
				return false;
		} else if (!ceiling.equals(other.ceiling))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (enumvalues == null) {
			if (other.enumvalues != null)
				return false;
		} else if (!enumvalues.equals(other.enumvalues))
			return false;
		if (floor == null) {
			if (other.floor != null)
				return false;
		} else if (!floor.equals(other.floor))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		if (unit == null) {
			if (other.unit != null)
				return false;
		} else if (!unit.equals(other.unit))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "ParameterInfo [type=" + type + ", enumvalues=" + enumvalues
				+ ", attrib=" + attrib + ", unit=" + unit + ", floor=" + floor
				+ ", ceiling=" + ceiling + ", description=" + description + "]";
	}
	
	
	
}
