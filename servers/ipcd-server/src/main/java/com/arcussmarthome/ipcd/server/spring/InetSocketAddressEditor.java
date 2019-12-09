package com.arcussmarthome.ipcd.server.spring;

import java.beans.PropertyEditorSupport;
import java.net.InetSocketAddress;

import org.apache.commons.lang3.StringUtils;

//import org.apache.commons.lang.StringUtils;

public class InetSocketAddressEditor extends PropertyEditorSupport {

	public String getAsText() {
		InetSocketAddress addr = (InetSocketAddress) getValue();
		if (addr == null) {
			return "0.0.0.0:0";
		}
		return addr.toString();
	}

	public void setAsText(String text) throws IllegalArgumentException {

		if (StringUtils.isEmpty(text)) {
			throw new IllegalArgumentException("Internet address or hostname is null");
		}

		String [] parts = text.split("\\:", 2);
		String host = parts[0];
		int port;
		if(parts.length == 1) {
			port = 0;
		}
		else {
			try {
				port = Integer.parseInt(parts[1]);
			}
			catch(NumberFormatException e) {
				throw new IllegalArgumentException("InetSocketAddress must be of the form [host:port], invalid value: [" + text + "]");
			}
		}

		InetSocketAddress socketAddress = new InetSocketAddress(host, port);
		setValue(socketAddress);
	}

}
