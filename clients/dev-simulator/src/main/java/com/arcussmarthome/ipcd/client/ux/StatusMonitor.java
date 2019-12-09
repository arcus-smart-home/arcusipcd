package com.arcussmarthome.ipcd.client.ux;

import javax.swing.SwingUtilities;

import com.arcussmarthome.ipcd.client.comm.StatusCallback;
import com.arcussmarthome.ipcd.client.model.DeviceModel;

public class StatusMonitor implements StatusCallback {
	private final MainFrame mainFrame;
	
	public StatusMonitor(MainFrame mainFrame) {
		this.mainFrame = mainFrame;
	}

	@Override
	public void onlineStatus(final boolean isOnline) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.showOnline(isOnline);
			}
		});
	}

	@Override
	public void onDevice(final DeviceModel deviceModel) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.createControls(deviceModel);
			}
		});
	}

	@Override
	public void onSetParameter(final String name, final Object value) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				mainFrame.updateControl(name, value);
			}
		});
	}
}
