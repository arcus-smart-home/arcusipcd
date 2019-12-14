package com.arcussmarthome.ipcd.server.netty;

import javax.net.ssl.SSLEngine;

public class TlsHandler {

	private SSLEngine engine;
	
	public TlsHandler(SSLEngine engine) {
		this.engine = engine;
	}

}
