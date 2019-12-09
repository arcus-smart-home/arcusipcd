package com.arcussmarthome.ipcd.server.session;

import java.util.Date;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcussmarthome.ipcd.msg.Device;
import com.arcussmarthome.ipcd.msg.ServerMessage;
import com.arcussmarthome.ipcd.ser.IpcdSerializer;

public class IpcdSession {

	private static final Logger logger = LoggerFactory.getLogger(IpcdSession.class);
	private static final Logger comlog = LoggerFactory.getLogger("COMLOG");
	
	private Device device;
	private Date sessionStartTime;
	private Channel channel;
	private IpcdSerializer serializer = new IpcdSerializer();
	
	public IpcdSession(Channel channel) {
		this(null, channel);
		this.sessionStartTime = new Date();
	}
	
	public IpcdSession(Device device, Channel channel) {
		this.device = device;
		this.sessionStartTime = new Date();
		this.channel = channel;
	}
	
	public boolean isInitialized() {
		return device != null;
	}
	
	public Device getDevice() {
		return this.device;
	}
	
	public void setDevice(Device d) {
		this.device = d;
	}
	
	public Channel getChannel() {
		return this.channel;
	}
	
	public Date getSessionStartTime() {
		return this.sessionStartTime;
	}
	
	public void destroy() {
		// explicitly clear reference
		this.channel = null;
	}

	public void sendCommand(ServerMessage command) {
		// TODO - consider adding command to blocking queue, then dispatching 
		// messages in another thread -- would need to be modified to return 
		// a future
		
		// TODO - consider throwing an exception if this session is
		// not yet initialized
		String msg = serializer.toJson(command);
		if (comlog.isInfoEnabled()) {
			comlog.info("Server=>Device " + device + "@" + channel.remoteAddress() + "=>" + channel.localAddress() + " : " + msg);
		}

		channel.writeAndFlush(new TextWebSocketFrame(msg));

	}
	
	
	

}
