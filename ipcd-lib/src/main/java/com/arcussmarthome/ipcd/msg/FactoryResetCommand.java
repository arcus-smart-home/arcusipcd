package com.arcussmarthome.ipcd.msg;

public class FactoryResetCommand extends AbstractCommand {

	public FactoryResetCommand() {
		super(CommandType.FactoryReset);
	}

	@Override
	public String toString() {
		return "FactoryResetCommand [command=" + command + ", txnid=" + txnid + "]";
	}
	
}
