package com.arcussmarthome.ipcd.client.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

public class DeviceRegistry {

	public static DeviceRegistry REGISTRY = new DeviceRegistry();
	private static Map<String,DeviceModel> devices = new LinkedHashMap<String,DeviceModel>();
	private static Gson gson = new Gson();
	private static final Logger logger = LoggerFactory.getLogger(DeviceRegistry.class);
	
	protected DeviceRegistry() {
		
	}
	
	public static synchronized String[] getDeviceIds() {
		loadDeviceRegistry();
		return devices.keySet().toArray(new String[0]);
	}
	
	public static synchronized DeviceModel getDevice(String id) {
		DeviceModel device = devices.get(id);
		
		if (device != null) {
			return device;
		} else {
			device = loadDevice(id);
			if (device != null) {
				devices.put(id, device);
			}
		}
		return device;
		
	}
	
	public static synchronized void shutdown() {
		for (String id: devices.keySet()) {
			DeviceModel device = devices.get(id);
			saveDevice(device);
		}
	}
	
	private static void loadDeviceRegistry() {
		File driverDir = getDataPath();
		if (driverDir.isDirectory()) {
			String[] files = driverDir.list();
			for (String file : files) {
				if (file.endsWith(".dev")) {
					String id = file.substring(0, file.length() - 4);
					if (devices.get(id) == null) {
						DeviceModel device = loadDevice(id);
						devices.put(id, device);
					}
				}
			}
		}
	}
	
	private static DeviceModel loadDevice(String id) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(getDataPath(), id + ".dev"));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Reader r = new InputStreamReader(fis);
		DeviceModel device = gson.fromJson(r, DeviceModel.class);
		return device;
	} 
	
	private static void saveDevice(DeviceModel device) {
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(new File(getDataPath(), device.getId() + ".dev"));
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		String serialized = gson.toJson(device);
		try {
			fos.write(serialized.getBytes());
			fos.flush();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (Exception e) {}
		}
	}
	
	private static File getDataPath() {
		URL registryClassLocation = DeviceRegistry.class.getResource("DeviceRegistry.class");
		File base = null;
		logger.debug("DeviceRegistry class protocol : " + registryClassLocation.getProtocol());
		if (!"file".equalsIgnoreCase(registryClassLocation.getProtocol())) {
			try {
				base = new File(DeviceRegistry.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
				logger.debug("Using base : " + base);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}
		} else {
			base = new File(registryClassLocation.getPath()).getParentFile().getParentFile().getParentFile().getParentFile().getParentFile().getParentFile();
			logger.debug("Using base : " + base);
		}
		// TODO: This is really crude. If there is ever time, something better should be implemented to locate the device files.
		// Climb up the path from the base directory and look for devices directory.
		File dataPath = new File (base + "/devices");
		if (dataPath.isDirectory()) {
		   logger.info("Using device registry : " + dataPath);
		   return dataPath;
		}
		dataPath = new File (base.getParentFile().getPath() + "/devices");
		if (dataPath.isDirectory()) {
		   logger.info("Using device registry : " + dataPath);
		   return dataPath;
		}
		dataPath = new File (base.getParentFile().getParentFile().getPath() + "/devices");
		logger.info("Using device registry : " + dataPath);
		if (!dataPath.exists()) {
			dataPath.mkdirs();
		}
		return dataPath; 
	}
}
