package com.arcussmarthome.ipcd.server.session;

import java.util.LinkedHashMap;
import java.util.Map;

import com.arcussmarthome.ipcd.msg.Device;

public class SessionRegistry {

	private static final SessionRegistry _instance = new SessionRegistry();
	
	private Map<Device, IpcdSession> sessionMap;
	private Map<String, Device> deviceIndex;
	
	private SessionRegistry() {
		sessionMap = new LinkedHashMap<Device, IpcdSession>();
		deviceIndex = new LinkedHashMap<String, Device>();
	}
	
	public static IpcdSession getSession(Device d) {
		return _instance.sessionMap.get(d);
	}
	
	public static IpcdSession getByKey(String vendor, String model, String sn) {
		return getByKey(genDeviceKey(vendor, model, sn));
	}
	
	public static IpcdSession getByKey(String key) {
		Device d = _instance.deviceIndex.get(key);
		if (d == null) {
			return null;
		}
		return _instance.sessionMap.get(d);
	}
	
	public static void putSesion(IpcdSession session) {
		Device d = session.getDevice();
		_instance.sessionMap.put(d, session);
		_instance.deviceIndex.put(genDeviceKey(d), d);
	}
	
	public static void destroySession(IpcdSession session) {
		Device d = session.getDevice();
		IpcdSession s = _instance.sessionMap.get(d);
		s.destroy();
		_instance.deviceIndex.remove(genDeviceKey(d));
		_instance.sessionMap.remove(d);
	}
	
	public static String genDeviceKey(Device d) {
		if (d == null) return null;
		return genDeviceKey(d.getVendor(), d.getModel(), d.getSn());
			
	}

	public static String genDeviceKey(String vendor, String model, String sn) {
		return vendor + "-" + model + "-" + sn; 
	}
	

}
