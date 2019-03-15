package com.iris.ipcd.client.commands;

public class NewDevice extends AbstractCommand {

	@Override
	public Commands getType() {
		return Commands.NewDevice;
	}

}
