package com.arcussmarthome.ipcd.client.commands;

public class SetParameterValue extends AbstractCommand {

	@Override
	public Commands getType() {
		return Commands.SetParameterValue;
	}

}
