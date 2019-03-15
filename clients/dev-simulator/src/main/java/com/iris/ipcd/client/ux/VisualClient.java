package com.iris.ipcd.client.ux;

import com.iris.ipcd.client.comm.IpcdCommandClient;
import com.iris.ipcd.client.commands.CommandQueue;

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
