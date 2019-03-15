package com.iris.ipcd.msg;

public interface Response<T extends ServerMessage> extends ClientMessage {
	
	public T getRequest();
	
}
