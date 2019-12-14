package com.arcussmarthome.ipcd.client.comm;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshakerFactory;
import io.netty.handler.codec.http.websocketx.WebSocketVersion;
import io.netty.handler.ssl.SslHandler;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLEngine;

import com.arcussmarthome.ipcd.client.handler.DownloadHandler;
import com.arcussmarthome.ipcd.client.handler.FactoryResetHandler;
import com.arcussmarthome.ipcd.client.handler.GetDeviceInfoHandler;
import com.arcussmarthome.ipcd.client.handler.GetEventConfigurationHandler;
import com.arcussmarthome.ipcd.client.handler.GetParameterInfoHandler;
import com.arcussmarthome.ipcd.client.handler.GetParameterValuesHandler;
import com.arcussmarthome.ipcd.client.handler.GetReportConfigurationHandler;
import com.arcussmarthome.ipcd.client.handler.LeaveHandler;
import com.arcussmarthome.ipcd.client.handler.RebootHandler;
import com.arcussmarthome.ipcd.client.handler.SetDeviceInfoHandler;
import com.arcussmarthome.ipcd.client.handler.SetEventConfigurationHandler;
import com.arcussmarthome.ipcd.client.handler.SetParameterValuesHandler;
import com.arcussmarthome.ipcd.client.handler.SetReportConfigurationHandler;
import com.arcussmarthome.ipcd.client.model.DeviceModel;
import com.arcussmarthome.ipcd.client.model.ParameterDefinition;
import com.arcussmarthome.ipcd.client.model.ValidationException;
import com.arcussmarthome.ipcd.client.scheduler.Scheduler;
import com.arcussmarthome.ipcd.msg.Event;
import com.arcussmarthome.ipcd.msg.EventAction;
import com.arcussmarthome.ipcd.msg.ReportAction;
import com.arcussmarthome.ipcd.msg.ReportConfiguration;
import com.arcussmarthome.ipcd.msg.ThresholdRule;
import com.arcussmarthome.ipcd.msg.ValueChange;
import com.arcussmarthome.ipcd.msg.ValueChangeThreshold;
import com.arcussmarthome.ipcd.ser.IpcdSerializer;

public class IpcdClientDevice {
	
	private Channel channel = null;
	private DeviceModel deviceModel = null;
	private EventLoopGroup group = null;
	private IpcdSerializer serializer;
	private Date bootTime;
	private Integer reportInterval = 60;
	private StatusCallback statusCallback;
	
	public IpcdClientDevice(EventLoopGroup group, DeviceModel deviceModel, StatusCallback statusCallback) {
		this.deviceModel = deviceModel;
		this.group = group;
		this.serializer = new IpcdSerializer();
		this.statusCallback = statusCallback;
	}
	
	public void connect() throws InterruptedException, URISyntaxException {
		final URI uri = new URI(deviceModel.getConnectUrl());
		final IpcdClientHandler handler = createClientHandler(uri);
		final String protocol = uri.getScheme();
        int defaultPort;
        
        ChannelInitializer<SocketChannel> initializer;

        if ("ws".equals(protocol)) {
            initializer = new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline()
                      .addLast("http-codec", new HttpClientCodec())
                      .addLast("aggregator", new HttpObjectAggregator(8192))
                      .addLast("ws-handler", handler);
                }
            };

            defaultPort = 80;

        } else if ("wss".equals(protocol)) {
            initializer = new ChannelInitializer<SocketChannel>() {
                @Override
                public void initChannel(SocketChannel ch) throws Exception {
                    SSLEngine engine = IpcdClientSslContextFactory.getContext().createSSLEngine();
                    engine.setUseClientMode(true);

                    ch.pipeline()
                      .addFirst("ssl", new SslHandler(engine))
                      .addLast("http-codec", new HttpClientCodec())
                      .addLast("aggregator", new HttpObjectAggregator(8192))
                      .addLast("ws-handler", handler);
                }
            };

            defaultPort = 443;
        } else {
            throw new IllegalArgumentException("Unsupported protocol: " + protocol);
        }
        
        Bootstrap b = new Bootstrap();
        b.group(group)
         .channel(NioSocketChannel.class)
         .handler(initializer);

        int port = uri.getPort();
        // If no port was specified, we'll try the default port: https://tools.ietf.org/html/rfc6455#section-1.7
        if (uri.getPort() == -1) {
            port = defaultPort;
        }

        channel = b.connect(uri.getHost(), port).sync().channel();
        handler.handshakeFuture().sync();
        
        doBoot();
		
		// set up report schedule
		Scheduler.getInstance().scheduleReport(this, this.reportInterval);
		
		if (statusCallback != null) {
			statusCallback.onlineStatus(true);
		}
            
	} // End Method
	
	public void disconnect() {
		if (channel != null) {
			channel.close();
			channel = null;
		}
		deviceModel.cancelFutures();
		if (statusCallback != null) {
			statusCallback.onlineStatus(true);
		}
	}
	
	public Channel getCurrentChannel() {
		return channel;
	}
	
	public DeviceModel getDeviceModel() {
		return deviceModel;
	}
	
	public Integer getUptime() {
		return (int)((System.currentTimeMillis() - this.bootTime.getTime()) / 1000);
	}
	
	public Object getDeviceInfoField(String field) {
		if (field.equalsIgnoreCase("uptime")) {
			return getUptime();
		}
		if (field.equalsIgnoreCase("connectUrl")) {
			return deviceModel.getConnectUrl();
		}
		if (field.equalsIgnoreCase("connection")) {
			return deviceModel.getConnectionType();
		}
		if (field.equalsIgnoreCase("fwver")) {
			return deviceModel.getFwver();
		}
		if (field.equalsIgnoreCase("commands")) {
			return deviceModel.getSupportedCommands();
		}
		if (field.equalsIgnoreCase("actions")) {
			return deviceModel.getSupportedActions();
		}
		return null;
	}
	
	public ReportConfiguration getReportConfiguration() {
		ReportConfiguration reportConfig = new ReportConfiguration();
		reportConfig.setInterval(reportInterval);
		List<String> reportParameterNames = deviceModel.getReportParameterNames();
		if (reportParameterNames.isEmpty()) {
			List<String> parameterNames = deviceModel.getParameterNames();
			reportConfig.setParameters(parameterNames);
		} else {
			reportConfig.setParameters(reportParameterNames);
		}
		return reportConfig;
	}
	
	public void setParameterValue(String name, Object value) throws ValidationException {
		deviceModel.setParameterValue(name, value);
		if (statusCallback != null) {
			statusCallback.onSetParameter(name, value);
		}
		evaluateValueChangeThresholds(deviceModel.getParameter(name));
	}
	
	public void setReportInterval(Integer intervalSec) throws ValidationException {
		if (intervalSec < 1) throw new ValidationException("Report interval (seconds) must be >= 1.");
		this.reportInterval = intervalSec;
		Scheduler.getInstance().scheduleReport(this, this.reportInterval);
	}
	
	public void doBoot() {
		this.bootTime = new Date();
		// send onBoot, onConnect
		
		doEvent( Arrays.asList(Event.onBoot, Event.onConnect) );
	}	
	
	public void doReboot(List<Event> events) {
		this.bootTime = new Date();
		// TODO close connection, re-connect
		
		// send onBoot, onConnect
		doEvent(events);
	}

	public void doFactoryReset() {
		//---------------------------------
		// TODO implement
	}
	
	public void doUpgrade(List<Event> events) {
		// TODO close connection, re-connect
		
		// send onBoot, onConnect, onUpgrade
		this.bootTime = new Date();
		
		doEvent(events);
	}
	
	public void doReport() {
		ReportAction report = new ReportAction();
		report.setDevice(deviceModel.getDevice());
		Map<String,Object> reportMap = new LinkedHashMap<String,Object>();
		for (String parameter: getReportConfiguration().getParameters()) {
			reportMap.put(parameter, deviceModel.getParameterValue(false, parameter));
		}
		report.setReport(reportMap);
		sendMessage(serializer.toJson(report));
	}
	
	public void doEvent(List<Event> events) {
		List<Event> eventCodes = new ArrayList<Event>();
		for (Event e: events) {
			if (deviceModel.isSupportedEvent(e)) {
				eventCodes.add(e);
			} else {
				// TODO log
			}
		}
		if (eventCodes.isEmpty()) return;
		
		EventAction event = new EventAction();
		event.setDevice(deviceModel.getDevice());
		event.setEvents(eventCodes);
		sendMessage(serializer.toJson(event));
	}
	
	public void sendMessage(Object msg, boolean serializeNulls) {
		if (msg != null) {
			String json = serializeNulls ? serializer.toJsonSerializeNulls(msg) : serializer.toJson(msg);
			sendMessage(json);
		}
	}
	
	private boolean isConnected () {
		return (channel != null && channel.isActive());
	}
	
	private void sendMessage(String json) {
		if (!isConnected()) {
			throw new IllegalStateException("Cannot send message because not connected");
		}
		
		int buffersize = deviceModel.getBuffersize();
		
		int startPos = 0;
    	TextWebSocketFrame respFrame = new TextWebSocketFrame(
    			startPos + buffersize >= json.length(),
    			0,
    			json.substring(startPos, Math.min(json.length(), (startPos + buffersize)))
    		);
    	channel.writeAndFlush(respFrame);
    	startPos += buffersize;
    	while (startPos < json.length()) {
    		ContinuationWebSocketFrame contFrame = new ContinuationWebSocketFrame(
    					startPos + buffersize >= json.length(),
    					0,
    					json.substring(startPos, Math.min(json.length(), (startPos + buffersize)))
    				);
    		startPos += buffersize;
    		channel.writeAndFlush(contFrame);
    	}
	}
	
    private IpcdClientHandler createClientHandler(java.net.URI uri) {
    	final IpcdClientHandler handler =
                new IpcdClientHandler(
                        WebSocketClientHandshakerFactory.newHandshaker(
                                uri, WebSocketVersion.V13, null, false, new DefaultHttpHeaders()), statusCallback);

        
        handler.setDownloadHandler(new DownloadHandler(this));
        handler.setFactoryResetHandler(new FactoryResetHandler(this));
        handler.setLeaveHandler(new LeaveHandler(this));
        handler.setRebootHandler(new RebootHandler(this));
        handler.setGetDeviceInfoHandler(new GetDeviceInfoHandler(this));
        handler.setGetEventConfigurationHandler(new GetEventConfigurationHandler(this));
        handler.setGetParameterInfoHandler(new GetParameterInfoHandler(this));
        handler.setGetParameterValuesHandler(new GetParameterValuesHandler(this));
        handler.setGetReportConfigurationHandler(new GetReportConfigurationHandler(this));
        handler.setSetDeviceInfoHandler(new SetDeviceInfoHandler(this));
        handler.setSetEventConfigurationHandler(new SetEventConfigurationHandler(this));
        handler.setSetParameterValuesHandler(new SetParameterValuesHandler(this));
        handler.setSetReportConfigurationHandler(new SetReportConfigurationHandler(this));
        
        return handler;
    }
    
    private void evaluateValueChangeThresholds(ParameterDefinition param) {
		ValueChangeThreshold thresholds = param.getEnabledValueChanges();
		boolean fireValueChange = false;
		List<ValueChange> valueChanges = new ArrayList<ValueChange>();
		if (thresholds.isOnChange()) {
			fireValueChange = true;
			ValueChange vc = new ValueChange(param.getName(), param.getCurrentValue(false), ThresholdRule.onChange, true);
			valueChanges.add(vc);
		}
		
		// TODO implement onChangeBy
		if (thresholds.getOnChangeBy() != null) {
			if (param.getLastValueChangeValue() != null) {
				Double val = (Double)param.getCurrentValue(false);
				Double lastVal = (Double)param.getLastValueChangeValue();
				if (Math.abs(val - lastVal) > thresholds.getOnChangeBy()) {
					fireValueChange = true;
					ValueChange vc = new ValueChange(param.getName(), param.getCurrentValue(false), ThresholdRule.onChangeBy, thresholds.getOnChangeBy());
					valueChanges.add(vc);
					param.setLastValueChangeValue(param.getCurrentValue(false));
				}
			} else {
				fireValueChange = true;
				ValueChange vc = new ValueChange(param.getName(), param.getCurrentValue(false), ThresholdRule.onChangeBy, thresholds.getOnChangeBy());
				valueChanges.add(vc);
				param.setLastValueChangeValue(param.getCurrentValue(false));
			}
		}
		
		if (thresholds.getOnEquals() != null) {
			for (Object eqvalue : thresholds.getOnEquals()) {
				if (eqvalue.equals(param.getCurrentValue(false))) {
					fireValueChange = true;
					ValueChange vc = new ValueChange(param.getName(), param.getCurrentValue(false), ThresholdRule.onEquals, eqvalue);
					valueChanges.add(vc);
				}
			}
		}
		if (thresholds.getOnGreaterThan() != null && thresholds.getOnGreaterThan().equals(param.getCurrentValue(false))) {
			fireValueChange = true;
			ValueChange vc = new ValueChange(param.getName(), param.getCurrentValue(false), ThresholdRule.onGreaterThan, thresholds.getOnGreaterThan());
			valueChanges.add(vc);
		}
		if (thresholds.getOnLessThan() != null && thresholds.getOnLessThan().equals(param.getCurrentValue(false))) {
			fireValueChange = true;
			ValueChange vc = new ValueChange(param.getName(), param.getCurrentValue(false), ThresholdRule.onLessThan, thresholds.getOnLessThan());
			valueChanges.add(vc);
		}
		
		if (fireValueChange) {
			EventAction event = new EventAction();
			event.setDevice(deviceModel.getDevice());
			event.setEvents(Arrays.asList(Event.onValueChange));
			event.setValueChanges(valueChanges);
			sendMessage(serializer.toJson(event));
		}
	}
}
