package com.arcussmarthome.ipcd.msg;

public interface Response<T extends ServerMessage> extends ClientMessage {
	
	public T getRequest();
	
}
