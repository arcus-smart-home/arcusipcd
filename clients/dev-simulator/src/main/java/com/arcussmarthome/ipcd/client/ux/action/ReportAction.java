package com.arcussmarthome.ipcd.client.ux.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;

import com.arcussmarthome.ipcd.client.commands.CommandQueue;
import com.arcussmarthome.ipcd.client.commands.DoReport;

public class ReportAction extends CommandAction {
	private static final long serialVersionUID = 1L;

	public ReportAction(CommandQueue commandQueue) {
		super("Do Report", commandQueue);
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_R));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			commandQueue.insertCommand(new DoReport());
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
