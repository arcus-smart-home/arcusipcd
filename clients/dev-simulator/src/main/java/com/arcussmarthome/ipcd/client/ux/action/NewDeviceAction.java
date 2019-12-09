package com.arcussmarthome.ipcd.client.ux.action;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.Action;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

import com.arcussmarthome.ipcd.client.commands.CommandQueue;
import com.arcussmarthome.ipcd.client.commands.NewDevice;
import com.arcussmarthome.ipcd.client.model.DeviceRegistry;

public class NewDeviceAction extends CommandAction {
	private static final long serialVersionUID = 1L;
	private JFrame parent;

	public NewDeviceAction(JFrame parent, CommandQueue commandQueue) {
		super("Select New Device...", commandQueue);
		this.parent = parent;
		putValue(Action.MNEMONIC_KEY, new Integer(KeyEvent.VK_D));
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		DevicePicker picker = new DevicePicker(parent);
		picker.pack();
		picker.setLocationRelativeTo(parent);
		picker.setVisible(true);
		if (picker.getSelectedDevice() != null && !picker.getSelectedDevice().isEmpty()) {
			NewDevice newDevice = new NewDevice();
			newDevice.putAttribute("device", picker.getSelectedDevice());
			try {
				commandQueue.insertCommand(newDevice);
			} catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	private class DevicePicker extends JDialog {
		private static final long serialVersionUID = 1L;
		private String deviceId = null;
		
		DevicePicker(JFrame frame) {
			super(frame);
			setTitle("Device Picker");
			setModal(true);
			
			String[] deviceIds = DeviceRegistry.getDeviceIds();
			JPanel p = new JPanel();
			p.setLayout(new BoxLayout(p, BoxLayout.PAGE_AXIS));
			final JComboBox<String> comboBox = new JComboBox<String>(deviceIds);
			JPanel buttonPanel = new JPanel();
			buttonPanel.setLayout(new FlowLayout());
			JButton okButton = new JButton("SELECT");
			okButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deviceId = (String)comboBox.getSelectedItem();
					setVisible(false);
				}
			});
			JButton cancelButton = new JButton("CANCEL");
			cancelButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					deviceId = null;
					setVisible(false);
				}
			});
			buttonPanel.add(cancelButton);
			buttonPanel.add(okButton);
			p.add(comboBox);
			p.add(buttonPanel);
			setContentPane(p);
		}
		
		String getSelectedDevice() {
			return deviceId;
		}
		
	}
}
