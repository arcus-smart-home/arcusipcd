package com.iris.ipcd.msg;

public interface ServerMessage extends Message {

	public CommandType getCommand();
	public String getTxnid();
	
}
