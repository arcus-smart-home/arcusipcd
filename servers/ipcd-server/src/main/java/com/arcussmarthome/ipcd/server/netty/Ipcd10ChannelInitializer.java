package com.arcussmarthome.ipcd.server.netty;

import javax.net.ssl.SSLEngine;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.ssl.SslHandler;

public class Ipcd10ChannelInitializer extends ChannelInitializer<SocketChannel>{

	private IpcdServerTlsContext serverTlsContext;
	
	public Ipcd10ChannelInitializer(IpcdServerTlsContext tlsContext) {
		this.serverTlsContext = tlsContext;
	}
	
	public Ipcd10ChannelInitializer() {
		this.serverTlsContext = null;
	}
	
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline pipeline = ch.pipeline();
		
		if (serverTlsContext != null && serverTlsContext.useTls()) {
			SSLEngine engine = serverTlsContext.getContext().createSSLEngine();
			engine.setUseClientMode(false);
			pipeline.addLast("tls", new SslHandler(engine));
		}
		
		pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("decoder", new HttpRequestDecoder());
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("handler", new Ipcd10WebSocketServerHandler(false));
	}


}
