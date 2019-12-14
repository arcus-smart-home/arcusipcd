package com.arcussmarthome.ipcd.client.comm;

import io.netty.buffer.Unpooled;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcussmarthome.ipcd.client.model.DeviceModel;
import com.arcussmarthome.ipcd.client.model.DeviceRegistry;
import com.arcussmarthome.ipcd.client.model.ValidationException;
import com.arcussmarthome.ipcd.msg.Event;

public class IpcdClient implements Runnable {
	private String connectUrl;
	private final String deviceModelName;
	private static final Logger logger = LoggerFactory.getLogger(IpcdClient.class);
	
	public IpcdClient(String connectUrl, String deviceModelName) {
		this.connectUrl = connectUrl;
        this.deviceModelName = deviceModelName;
    }

    public void run() {
        EventLoopGroup group = new NioEventLoopGroup();
        
        logger.info("Using device " + deviceModelName);
        
        try {
        	
        	// TODO better init
            DeviceModel deviceModel = DeviceRegistry.getDevice(deviceModelName);
            
            if (connectUrl != null && connectUrl.length() > 0) {
            	deviceModel.setConnectUrl(connectUrl);
            }
            
            IpcdClientDevice clientDevice = new IpcdClientDevice(group, deviceModel, null);
            
            clientDevice.connect();
            
            // TODO -- rather than read from console, change this to read msgs from a blocking queue
            
            BufferedReader console = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String msg = console.readLine();
                if (msg == null) {
                    break;
                }
                
                String[] parts = msg.split(" ");
                if ("bye".equals(parts[0].toLowerCase())) {
                    clientDevice.getCurrentChannel().writeAndFlush(new CloseWebSocketFrame());
                    clientDevice.getCurrentChannel().closeFuture().sync();
                    break;
                } else if ("ping".equals(parts[0].toLowerCase())) {
                    WebSocketFrame frame = new PingWebSocketFrame(Unpooled.copiedBuffer(new byte[]{8, 1, 8, 1}));
                    clientDevice.getCurrentChannel().writeAndFlush(frame);
                } else if ("report".equals(parts[0].toLowerCase())) {
                	clientDevice.doReport();
                } else if ("event".equals(parts[0].toLowerCase())) {
                	List<Event> eventCodes = new ArrayList<Event>();
                	for (int i = 1; i < parts.length; i++) {
                		Event e = Event.valueOf(parts[i]);
                		eventCodes.add(e);
                	}
                	clientDevice.doEvent(eventCodes);
                } else if ("set".equals(parts[0].toLowerCase())) {
                	setParameterValue(clientDevice, parts);
                }
                
            }
        } catch (Exception ex){
        	logger.error("Network Exception", ex);
        } finally {
            group.shutdownGracefully();
        }
    }

    private void setParameterValue(IpcdClientDevice clientDevice, String[] parts) {
    	Map<String,Object> values = new LinkedHashMap<String,Object>();
		for (int i = 1; i < parts.length; i++) {
			String value = parts[i];
			String[] pair = value.split("=");
			if (pair.length == 2) { 
				values.put(pair[0], castValue(pair[1]));
			} else {
				logger.error("error: usage for set command is\nset param1=value1 [param2=value2] ...");
			}
		}
		
		for (String param : values.keySet()) {
			try {
				clientDevice.setParameterValue(param, values.get(param));
			} catch (ValidationException e) {
				logger.error("error: Validation Exception " + e.getLocalizedMessage());
			}
		}
    }
    
private Object castValue(String v) {
		
		if (isQuoted(v)) {
			return v.substring(1, v.length() - 1);
		}
		
		Object value;
		try {
			value = Integer.valueOf(v);
			return value;
		} catch (Exception e) {}
		
		try {
			value = Double.valueOf(v);
			return value;
		} catch (Exception e) {}
		
		if ("true".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v)) {
			try {
				value = Boolean.valueOf(v);
				return value;
			} catch (Exception e) {}
		}
		return v;
	}
	
	private boolean isQuoted(String s) {
		return (s != null && s.length() >= 2 && s.startsWith("\"") && s.endsWith("\""));
	}
    
    public static void main(String... args) throws Exception {
        String deviceName;
        String connectUrl = args.length > 0 ? args[0] : null;
        
        if (args.length > 1) {
        	deviceName = args[1];
        } else {
        	deviceName = "BlackBox-Multisensor2-00049B3C7A05";
        }
        
        IpcdClient ipcdClient  = new IpcdClient(connectUrl, deviceName);
        ipcdClient.run();
    }
}
