package com.arcussmarthome.ipcd.server.netty;

import java.io.StringReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcussmarthome.ipcd.msg.ClientMessage;
import com.arcussmarthome.ipcd.msg.ServerMessage;
import com.arcussmarthome.ipcd.ser.IpcdSerializer;
import com.arcussmarthome.ipcd.server.index.IpcdServerIndexPage;
import com.arcussmarthome.ipcd.server.session.IpcdSession;
import com.arcussmarthome.ipcd.server.session.SessionRegistry;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.CloseWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PingWebSocketFrame;
import io.netty.handler.codec.http.websocketx.PongWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshaker;
import io.netty.handler.codec.http.websocketx.WebSocketServerHandshakerFactory;
import io.netty.util.CharsetUtil;
import static io.netty.handler.codec.http.HttpHeaders.Names.*;
import static io.netty.handler.codec.http.HttpHeaders.*;
import static io.netty.handler.codec.http.HttpMethod.*;
import static io.netty.handler.codec.http.HttpResponseStatus.*;
import static io.netty.handler.codec.http.HttpVersion.*;

/**
 * Websocket server handler for IPCD protocol version 1.0
 * 
 * @author sperry
 *
 */
public class Ipcd10WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger logger = LoggerFactory.getLogger(Ipcd10WebSocketServerHandler.class);
	private static final Logger comlog = LoggerFactory.getLogger("COMLOG"); 
	
	private boolean useSSL = false;
	private IpcdSession session = null;
	
	public Ipcd10WebSocketServerHandler(boolean useSSL) {
		this.useSSL = useSSL;
	}
	
	private static final String WEBSOCKET_PATH = "/ipcd/1.0";

    private WebSocketServerHandshaker handshaker;
    
    // Buffer to build JSON from text and continuation frames.
    private StringBuffer jsonBuffer = null;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {
            handleWebSocketFrame(ctx, (WebSocketFrame) msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) throws Exception {
        // Handle a bad request.
        if (!req.getDecoderResult().isSuccess()) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.getMethod() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        // Send the demo page and favicon.ico
        if ("/".equals(req.getUri())) {
            ByteBuf content = IpcdServerIndexPage.getContent(getWebSocketLocation(req));
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, content);

            res.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
            setContentLength(res, content.readableBytes());

            sendHttpResponse(ctx, req, res);
            return;
        }

        if ("/favicon.ico".equals(req.getUri())) {
            FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, NOT_FOUND);
            sendHttpResponse(ctx, req, res);
            return;
        }

        // Handshake
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(getWebSocketLocation(req), null, false);
        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
            handshaker.handshake(ctx.channel(), req);
            // Only create the session after the handshake.
            // at this point the session is not fully initialized 
            // because we haven't gotten a message from the device that 
            // can identify it.  We only put the session in the registry when it
            // is initialized
            session = new IpcdSession(ctx.channel());
            
        }
    }

    private void handleWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {

        // Check for closing frame
        if (frame instanceof CloseWebSocketFrame) {
        	handleMessageCompleted(ctx, jsonBuffer.toString());
            handshaker.close(ctx.channel(), (CloseWebSocketFrame) frame.retain());
            SessionRegistry.destroySession(session);
            return;
        }
        if (frame instanceof PingWebSocketFrame) {
        	if (logger.isDebugEnabled())
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        if (frame instanceof TextWebSocketFrame) {
        	jsonBuffer = new StringBuffer();
        	jsonBuffer.append(((TextWebSocketFrame)frame).text());
        }
        else if (frame instanceof ContinuationWebSocketFrame) {
        	if (jsonBuffer != null) {
        		jsonBuffer.append(((ContinuationWebSocketFrame)frame).text());
        	}
        	else {
        		comlog.warn("Continuation frame received without initial frame.");
        	}
        }
        else {
        	throw new UnsupportedOperationException(String.format("%s frame types not supported", frame.getClass()
                    .getName()));
        }

        // Check if Text or Continuation Frame is final fragment and handle if needed.
        if (frame.isFinalFragment()) {
        	handleMessageCompleted(ctx, jsonBuffer.toString());
        	jsonBuffer = null;
        }
    }
    
    private void handleMessageCompleted(ChannelHandlerContext ctx, String json) {
    	// parse the message
        if (logger.isDebugEnabled()) {
            //logger.debug(String.format("%s received %s", ctx.channel(), json));
        	comlog.info("Device=>Server " + ctx.channel().remoteAddress() + "=>" + ctx.channel().localAddress() + " : " + json);
        }
        
        IpcdSerializer ser = new IpcdSerializer();
        ClientMessage msg = ser.parseClientMessage(new StringReader(json));
        
        // set device on the session if the session is not yet initialized
        if (!session.isInitialized()) {
        	session.setDevice(msg.getDevice());
        	SessionRegistry.putSesion(session);
        }
        
        ServerMessage response = handleClientMessage(msg);
        
        if (response != null) {
        	ctx.channel().write(new TextWebSocketFrame(ser.toJson(response)));
        }
    }
    
    private ServerMessage handleClientMessage(ClientMessage msg) {
		
    	// TODO 
    	// dispatch this message to consumers
    	
    	return null;
	}
    

    private void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        // Generate an error page if response getStatus code is not OK (200).
        if (res.getStatus().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        // Send the response and close the connection if necessary.
        ChannelFuture f;
        if (useSSL) {
        	f = ctx.channel().writeAndFlush(res);
        } else {
        	// TODO may not want to flush here -- only write
        	f = ctx.channel().writeAndFlush(res);	
        }
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private String getWebSocketLocation(FullHttpRequest req) {
        return (useSSL)? "wss://" : "ws://" + 
        		req.headers().get(HOST) + WEBSOCKET_PATH;
    }

}
