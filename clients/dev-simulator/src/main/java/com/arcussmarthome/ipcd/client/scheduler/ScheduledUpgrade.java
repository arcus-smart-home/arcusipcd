package com.arcussmarthome.ipcd.client.scheduler;

import java.util.Arrays;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.Event;

public class ScheduledUpgrade implements Runnable {

	private static final Logger logger = LoggerFactory.getLogger(ScheduledUpgrade.class);
	
	private IpcdClientDevice device;
	private String upgradeURL;
	
	public ScheduledUpgrade(IpcdClientDevice device, String upgradeURL) {
		this.device = device;
		this.upgradeURL = upgradeURL;
	}
	
	public void run() {
		// fetch image
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			HttpGet httpGet = new HttpGet(upgradeURL);
			CloseableHttpResponse response1 = null;
			//logger.info("Downloading " + upgradeURL);
			response1 = httpclient.execute(httpGet);
		} catch (Exception e) {
			// ClientProtocolException or IOException
			logger.error("Download failure", e);
			device.doEvent(Arrays.asList(Event.onDownloadFailed));
		} 
		device.doEvent(Arrays.asList(Event.onDownloadComplete));
		
		// simulate time to unpack and verify
		try {
			Thread.sleep(device.getDeviceModel().getSimulatedUpgradeInterval() * 1000);
		} catch (InterruptedException e) {
			logger.debug("upgrade thread interrupted", e);
		}
		device.doEvent(Arrays.asList(Event.onBoot, Event.onConnect, Event.onUpdate));
		
	}
	

}
