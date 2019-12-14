package com.arcussmarthome.ipcd.client.commands;

public class NewDevice extends AbstractCommand {

	@Override
	public Commands getType() {
		return Commands.NewDevice;
	}

}
