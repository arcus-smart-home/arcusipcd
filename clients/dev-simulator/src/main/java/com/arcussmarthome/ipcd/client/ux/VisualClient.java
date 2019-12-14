package com.arcussmarthome.ipcd.client.ux;

import com.arcussmarthome.ipcd.client.comm.IpcdCommandClient;
import com.arcussmarthome.ipcd.client.commands.CommandQueue;

public class VisualClient {
	public static void main(String[] args) {
		String deviceName;
        String connectUrl = args.length > 0 ? args[0] : null;
        
        if (args.length > 1) {
        	deviceName = args[1];
        } else {
        	deviceName = "BlackBox-ContactSensor1-05102034A7B5";
        }
        
		CommandQueue commandQueue = new CommandQueue();
		MainFrame mainFrame = new MainFrame(commandQueue);
		mainFrame.setVisible(true);
		
		StatusMonitor statusMonitor = new StatusMonitor(mainFrame);
		IpcdCommandClient ipcdClient = new IpcdCommandClient(commandQueue, statusMonitor, connectUrl, deviceName);
		
		Thread clientThread = new Thread(ipcdClient);
		clientThread.start();
	}
}
