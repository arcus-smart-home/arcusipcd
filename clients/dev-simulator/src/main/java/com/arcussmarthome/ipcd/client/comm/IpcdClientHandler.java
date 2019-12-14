package com.arcussmarthome.ipcd.client.comm;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.util.CharsetUtil;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.arcussmarthome.ipcd.msg.DownloadCommand;
import com.arcussmarthome.ipcd.msg.FactoryResetCommand;
import com.arcussmarthome.ipcd.msg.GetDeviceInfoCommand;
import com.arcussmarthome.ipcd.msg.GetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.GetParameterInfoCommand;
import com.arcussmarthome.ipcd.msg.GetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.GetReportConfigurationCommand;
import com.arcussmarthome.ipcd.msg.LeaveCommand;
import com.arcussmarthome.ipcd.msg.RebootCommand;
import com.arcussmarthome.ipcd.msg.ServerMessage;
import com.arcussmarthome.ipcd.msg.SetDeviceInfoCommand;
import com.arcussmarthome.ipcd.msg.SetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.SetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.SetReportConfigurationCommand;
import com.arcussmarthome.ipcd.ser.IpcdSerializer;

public class IpcdClientHandler extends SimpleChannelInboundHandler<Object>{

	private static final Logger logger = LoggerFactory.getLogger(IpcdClientHandler.class);
	
	private final WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;
    private IpcdSerializer serializer = new IpcdSerializer();
    private StatusCallback statusCallback;
    
    private DownloadHandler downloadHandler;
    private FactoryResetHandler factoryResetHandler;
    private LeaveHandler leaveHandler;
    private RebootHandler rebootHandler;
    private GetDeviceInfoHandler getDeviceInfoHandler;
    private GetEventConfigurationHandler getEventConfigurationHandler;
    private GetParameterInfoHandler getParameterInfoHandler;
    private GetParameterValuesHandler getParameterValuesHandler;
    private GetReportConfigurationHandler getReportConfigurationHandler;
    private SetDeviceInfoHandler setDeviceInfoHandler;
    private SetEventConfigurationHandler setEventConfigurationHandler;
    private SetParameterValuesHandler setParameterValuesHandler;
    private SetReportConfigurationHandler setReportConfigurationHandler;
	
	public IpcdClientHandler(WebSocketClientHandshaker handshaker, StatusCallback statusCallback) {
        this.handshaker = handshaker;
        this.statusCallback = statusCallback;
    }

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        handshakeFuture = ctx.newPromise();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        handshaker.handshake(ctx.channel());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("WebSocket Client disconnected!");
        if (statusCallback != null) {
        	statusCallback.onlineStatus(false);
        }
    }

	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            handshaker.finishHandshake(ch, (FullHttpResponse) msg);
            logger.info("Ipcd client connected");
            handshakeFuture.setSuccess();
            return;
        }
        
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            Exception e = new Exception("Unexpected FullHttpResponse (getStatus=" + response.getStatus() + ", content="
                    + response.content().toString(CharsetUtil.UTF_8) + ')');
            logger.error("Unexpected FullHttpResponse after websocket connection", e);
            throw e;
        }

        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            
            logger.debug("received message: " + textFrame.text());
            
            // TODO handle message from server
            ServerMessage ipcdmsg = serializer.parseServerMessage(new StringReader(textFrame.text()));
            switch(ipcdmsg.getCommand()) {
	            case GetParameterValues:
	            	getParameterValuesHandler.handleCommand((GetParameterValuesCommand)ipcdmsg);
					break;
	            case SetParameterValues:
	            	setParameterValuesHandler.handleCommand((SetParameterValuesCommand)ipcdmsg);
					break;
	            case GetDeviceInfo:
	            	getDeviceInfoHandler.handleCommand((GetDeviceInfoCommand)ipcdmsg);
	            	break;
	            case SetDeviceInfo:
	            	setDeviceInfoHandler.handleCommand((SetDeviceInfoCommand)ipcdmsg);
	            	break;
				case Download:
					downloadHandler.handleCommand((DownloadCommand)ipcdmsg);
					break;
				case Reboot:
					rebootHandler.handleCommand((RebootCommand)ipcdmsg);
					break;
				case FactoryReset:
					factoryResetHandler.handleCommand((FactoryResetCommand)ipcdmsg);
					break;
				case Leave:
					leaveHandler.handleCommand((LeaveCommand)ipcdmsg);
					break;
				case GetParameterInfo:
					getParameterInfoHandler.handleCommand((GetParameterInfoCommand)ipcdmsg);
					break;
				case GetEventConfiguration:
					getEventConfigurationHandler.handleCommand((GetEventConfigurationCommand)ipcdmsg);
					break;
				case GetReportConfiguration:
					getReportConfigurationHandler.handleCommand((GetReportConfigurationCommand)ipcdmsg);
					break;
				case SetEventConfiguration:
					setEventConfigurationHandler.handleCommand((SetEventConfigurationCommand)ipcdmsg);
					break;
				case SetReportConfiguration:
					setReportConfigurationHandler.handleCommand((SetReportConfigurationCommand)ipcdmsg);
					break;
				default:
					break;
            }
        } else if (frame instanceof PongWebSocketFrame) {
            logger.debug("IPCD Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
            logger.info("WebSocket Client received closing");
            ch.close();
        }
		
	}
	
	@Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();

        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }

        ctx.close();
    }

	public void setDownloadHandler(DownloadHandler downloadHandler) {
		this.downloadHandler = downloadHandler;
	}

	public void setFactoryResetHandler(FactoryResetHandler factoryResetHandler) {
		this.factoryResetHandler = factoryResetHandler;
	}
	
	public void setLeaveHandler(LeaveHandler leaveHandler) {
		this.leaveHandler = leaveHandler;
	}

	public void setRebootHandler(RebootHandler rebootHandler) {
		this.rebootHandler = rebootHandler;
	}
	public void setGetDeviceInfoHandler(GetDeviceInfoHandler getDeviceInfoHandler) {
		this.getDeviceInfoHandler = getDeviceInfoHandler;
	}

	public void setGetEventConfigurationHandler(GetEventConfigurationHandler getEventConfigurationHandler) {
		this.getEventConfigurationHandler = getEventConfigurationHandler;
	}

	public void setGetParameterInfoHandler(GetParameterInfoHandler getParameterInfoHandler) {
		this.getParameterInfoHandler = getParameterInfoHandler;
	}

	public void setGetParameterValuesHandler(GetParameterValuesHandler getParameterValuesHandler) {
		this.getParameterValuesHandler = getParameterValuesHandler;
	}

	public void setGetReportConfigurationHandler(GetReportConfigurationHandler getReportConfigurationHandler) {
		this.getReportConfigurationHandler = getReportConfigurationHandler;
	}
	
	public void setSetDeviceInfoHandler(SetDeviceInfoHandler setDeviceInfoHandler) {
		this.setDeviceInfoHandler = setDeviceInfoHandler;
	}

	public void setSetEventConfigurationHandler(SetEventConfigurationHandler setEventConfigurationHandler) {
		this.setEventConfigurationHandler = setEventConfigurationHandler;
	}

	public void setSetParameterValuesHandler(SetParameterValuesHandler setParameterValuesHandler) {
		this.setParameterValuesHandler = setParameterValuesHandler;
	}

	public void setSetReportConfigurationHandler(SetReportConfigurationHandler setReportConfigurationHandler) {
		this.setReportConfigurationHandler = setReportConfigurationHandler;
	}
}
