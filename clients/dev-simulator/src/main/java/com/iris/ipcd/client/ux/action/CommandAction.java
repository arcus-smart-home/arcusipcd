package com.iris.ipcd.client.ux.action;

import javax.swing.AbstractAction;

import com.iris.ipcd.client.commands.CommandQueue;

public abstract class CommandAction extends AbstractAction {

	private static final long serialVersionUID = 1L;
	protected final CommandQueue commandQueue;

	public CommandAction(String title, CommandQueue commandQueue) {
		super(title);
		this.commandQueue = commandQueue;
	}

}
