package com.arcussmarthome.ipcd.msg;

public class Device {
	
	private String vendor;
	private String model;
	private String sn;
	private String ipcdver;
	
	public String getVendor() {
		return vendor;
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
	}
	public String getSn() {
		return sn;
	}
	public void setSn(String sn) {
		this.sn = sn;
	}
	public String getIpcdver() {
		return ipcdver;
	}
	public void setIpcdver(String ipcdver) {
		this.ipcdver = ipcdver;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ipcdver == null) ? 0 : ipcdver.hashCode());
		result = prime * result + ((model == null) ? 0 : model.hashCode());
		result = prime * result + ((sn == null) ? 0 : sn.hashCode());
		result = prime * result + ((vendor == null) ? 0 : vendor.hashCode());
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
		Device other = (Device) obj;
		if (ipcdver == null) {
			if (other.ipcdver != null)
				return false;
		} else if (!ipcdver.equals(other.ipcdver))
			return false;
		if (model == null) {
			if (other.model != null)
				return false;
		} else if (!model.equals(other.model))
			return false;
		if (sn == null) {
			if (other.sn != null)
				return false;
		} else if (!sn.equals(other.sn))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Device [vendor=" + vendor + ", model=" + model + ", sn=" + sn
				+ ", ipcdver=" + ipcdver + "]";
	}
	
	
}
