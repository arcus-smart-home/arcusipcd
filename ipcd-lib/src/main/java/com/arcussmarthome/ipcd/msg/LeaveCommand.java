package com.arcussmarthome.ipcd.msg;

public class LeaveCommand extends AbstractCommand {

	public LeaveCommand() {
		super(CommandType.Leave);
	}

	@Override
	public String toString() {
		return "LeaveCommand [command=" + command + ", txnid=" + txnid + "]";
	}
	
}
