package com.arcussmarthome.ipcd.client.comm;

import com.arcussmarthome.ipcd.client.model.DeviceModel;

public interface StatusCallback {
	void onlineStatus(boolean isOnline);
	void onDevice(DeviceModel deviceModel);
	void onSetParameter(String name, Object value);
}
