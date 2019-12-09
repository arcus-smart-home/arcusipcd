package com.arcussmarthome.ipcd.client.ux.action;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.arcussmarthome.ipcd.client.commands.CommandQueue;
import com.arcussmarthome.ipcd.client.commands.SetDeviceInfo;

public class ConnectAction extends CommandAction {
	private static final long serialVersionUID = 1L;
	private JFrame parent;

	public ConnectAction(JFrame parent, CommandQueue commandQueue) {
		super("Connect to Server...", commandQueue);
		this.parent = parent;
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_C));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		try {
			String server = JOptionPane.showInputDialog(parent, "Enter the IPCD server to connect to");
			if (server != null && !server.isEmpty()) {
				SetDeviceInfo setDeviceInfo = new SetDeviceInfo();
				setDeviceInfo.putAttribute("connectUrl", server);
				commandQueue.insertCommand(setDeviceInfo);
			}
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
