package com.arcussmarthome.ipcd.server.index;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

public class IpcdServerIndexPage {

	private static final String NEWLINE = "\r\n";
	
	public IpcdServerIndexPage() {
		// TODO Auto-generated constructor stub
	}
	
	public static ByteBuf getContent(String webSocketLocation) {
        return Unpooled.copiedBuffer(
        		"<html><head><title>IPCD Server</title></head>" + NEWLINE +
        		"<body>" + NEWLINE +
        		"<h2>Ipcd Server</h2>" + NEWLINE + 
        		"<div>TODO : content here</div>" + NEWLINE +
        		"</body>" + NEWLINE +
                "</html>" + NEWLINE, CharsetUtil.US_ASCII);
        		
	}

}
