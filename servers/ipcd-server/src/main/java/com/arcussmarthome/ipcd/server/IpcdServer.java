package com.arcussmarthome.ipcd.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import com.arcussmarthome.ipcd.server.debug.Console;

public class IpcdServer {

	public static final String DEFAULT_CONTEXT = "/ipcd-config.xml";
	
	private static final Logger logger = LoggerFactory.getLogger(IpcdServer.class);
	
	protected int portNumber = 443;
	protected InetSocketAddress socketBindAddress;
	protected boolean debugConsole = false;
	
	protected ChannelInitializer<SocketChannel> channelInitializer;
	protected EventLoopGroup bossGroup = null;
	protected EventLoopGroup workerGroup = null;
	@SuppressWarnings("rawtypes")
	protected Map<ChannelOption, Object> channelOptions;
	protected ServerBootstrap serverBootstrap;
	
	public IpcdServer() {
	}
	
	@SuppressWarnings( {"rawtypes", "unchecked"} )
	public void startServer() throws Exception {
		try {
			serverBootstrap = new ServerBootstrap();
			serverBootstrap.group(bossGroup, workerGroup);
			serverBootstrap.channel(NioServerSocketChannel.class);
			for (Map.Entry<ChannelOption, Object> e : channelOptions.entrySet()) {
				serverBootstrap.childOption(e.getKey(), e.getValue());
			}
			serverBootstrap.childHandler(channelInitializer);
			
			Channel ch = serverBootstrap.bind(portNumber).sync().channel();

			// TODO log msg server start
			
			if (debugConsole) {
	            BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
	            Console console = new Console();
	            while (true) {
	                String msg = consoleReader.readLine();
	                if (msg == null) {
	                    break;
	                }
	                console.handleInput(msg);
	            }
			} else {
	            ch.closeFuture().sync();
			}
            
		} finally {
			bossGroup.shutdownGracefully();
	        workerGroup.shutdownGracefully();
		}
		
	}
	
	public void shutdownServer() throws Exception {
	}
	
	
	public EventLoopGroup getBossGroup() {
		return bossGroup;
	}
	
	@Required
	public void setBossGroup(EventLoopGroup bossGroup) {
		this.bossGroup = bossGroup;
	}
	
	public EventLoopGroup getWorkerGroup() {
		return workerGroup;
	}
	
	
	@Required
	public void setWorkerGroup(EventLoopGroup workerGroup) {
		this.workerGroup = workerGroup;
	}

	@SuppressWarnings("rawtypes")
	public Map<ChannelOption, Object> getChannelOptions() {
		return channelOptions;
	}

	@SuppressWarnings("rawtypes")
	@Required
	public void setChannelOptions(Map<ChannelOption, Object> channelOptions) {
		this.channelOptions = channelOptions;
	}


	public int getPortNumber() {
		return portNumber;
	}

	@Required
	public void setPortNumber(int portNumber) {
		this.portNumber = portNumber;
	}

	public void setDebugConsole(boolean debugConsole) {
		this.debugConsole = debugConsole;
	}

	public InetSocketAddress getSocketBindAddress() {
		return socketBindAddress;
	}

	@Required
	public void setSocketBindAddress(InetSocketAddress socketBindAddress) {
		this.socketBindAddress = socketBindAddress;
	}


	public ChannelInitializer<SocketChannel> getChannelPipelineInitializer() {
		return channelInitializer;
	}

	@Required
	public void setChannelInitializer(ChannelInitializer<SocketChannel> channelInitializer) {
		this.channelInitializer = channelInitializer;
	}

	private static GenericApplicationContext loadContext(String contextLocation) {
		GenericApplicationContext app = new GenericApplicationContext();
		XmlBeanDefinitionReader reader = new XmlBeanDefinitionReader(app);
		if(IpcdServer.class.getResource(contextLocation) != null) {
			reader.loadBeanDefinitions(new ClassPathResource(contextLocation));
		}
		else if(new File(contextLocation).exists()) {
			reader.loadBeanDefinitions(new FileSystemResource(contextLocation));
		}
		else {
			return null;
		}
		app.refresh();
		return app;
	}

	public static void main(String[] args) throws Exception {
		String contextLocation = args.length > 0 ? args[0] : DEFAULT_CONTEXT;
		System.out.println("Loading configuration " + contextLocation + "...");
		final GenericApplicationContext ctx = loadContext(contextLocation);
		if(ctx == null) {
			System.err.println("Could not locate config file "+contextLocation+", exiting...");
			System.exit(-1);
		}
		
		Map<String, IpcdServer> servers = ctx.getBeansOfType(IpcdServer.class);
		if(servers.size() == 0) {
	         System.err.println("No IpcdServers defined, exiting...");
	         System.exit(-1);
	      }
	      else if(servers.size() > 1) {
	         System.err.println("More than 1 IpcdServer defined, exiting...");
	         System.exit(-1);
	      }
		
		final IpcdServer server = servers.values().iterator().next();
	      System.out.println("Starting server at " + server.getSocketBindAddress().getAddress() + ":" + server.getPortNumber());
	      server.startServer();
	}

	
	
}
