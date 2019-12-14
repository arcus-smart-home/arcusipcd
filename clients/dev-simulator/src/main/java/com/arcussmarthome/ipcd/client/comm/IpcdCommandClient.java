package com.arcussmarthome.ipcd.client.comm;

import java.net.URISyntaxException;
import java.util.Map;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcussmarthome.ipcd.client.commands.Command;
import com.arcussmarthome.ipcd.client.commands.CommandQueue;
import com.arcussmarthome.ipcd.client.commands.Commands;
import com.arcussmarthome.ipcd.client.model.DeviceModel;
import com.arcussmarthome.ipcd.client.model.DeviceRegistry;

public class IpcdCommandClient implements Runnable {
	private String connectUrl;
	private StatusCallback statusCallback;
	private String deviceModelName;
	private final CommandQueue commandQueue;
	private static final Logger logger = LoggerFactory.getLogger(IpcdClient.class);
	IpcdClientDevice clientDevice;
	
	public IpcdCommandClient(CommandQueue commandQueue, StatusCallback callback, String connectUrl, String deviceModelName) {
		this.commandQueue = commandQueue;
		this.connectUrl = connectUrl;
        this.deviceModelName = deviceModelName;
        this.statusCallback = callback;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        
        logger.info("Using device " + deviceModelName);
        
        try {
        	
        	// TODO better init
            initializeDevice(group);
                        
            while (true) {
            	Command command = commandQueue.poll();
            	if (command != null) {
            		if (command.getType() == Commands.DoReport) {
            			clientDevice.doReport();
            		}
            		else if (command.getType() == Commands.SetParameterValue) {
            			Map<String, String> attributes = command.getAttributes();
            			for (String attr : attributes.keySet()) {
            				clientDevice.setParameterValue(attr, attributes.get(attr));
            			}
            		}
            		else if (command.getType() == Commands.NewDevice) {
            			String newDeviceId = command.getAttributes().get("device");
            			clientDevice.disconnect();
            			deviceModelName = newDeviceId;
            			initializeDevice(group);
            		}
            		else if (command.getType() == Commands.SetDeviceInfo) {
            			Map<String, String> attributes = command.getAttributes();
            			for (String attr : attributes.keySet()) {
            				if (attr.equalsIgnoreCase("connectUrl")) {
            					clientDevice.getDeviceModel().setConnectUrl(attributes.get(attr));
            					clientDevice.disconnect();
            					clientDevice.connect();
            				}
            			}
            		}
            	}
            }
        } catch (Exception ex){
        	logger.error("Network Exception", ex);
        } finally {
            group.shutdownGracefully();
        }
    }

	private void initializeDevice(EventLoopGroup group)
			throws InterruptedException, URISyntaxException {
		DeviceModel deviceModel = DeviceRegistry.getDevice(deviceModelName);
		
		if (connectUrl != null && connectUrl.length() > 0) {
			deviceModel.setConnectUrl(connectUrl);
		}
		
		clientDevice = new IpcdClientDevice(group, deviceModel, statusCallback);
		
		statusCallback.onDevice(deviceModel);
		
		clientDevice.connect();
	}
}

