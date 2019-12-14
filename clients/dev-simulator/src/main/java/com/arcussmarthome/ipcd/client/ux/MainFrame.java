package com.arcussmarthome.ipcd.client.ux;

import java.awt.BorderLayout;
import java.awt.Rectangle;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;

import com.arcussmarthome.ipcd.client.commands.CommandQueue;
import com.arcussmarthome.ipcd.client.model.DeviceModel;
import com.arcussmarthome.ipcd.client.model.ParameterDefinition;
import com.arcussmarthome.ipcd.client.ux.action.ConnectAction;
import com.arcussmarthome.ipcd.client.ux.action.NewDeviceAction;
import com.arcussmarthome.ipcd.client.ux.action.ReportAction;

public class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;
	private final CommandQueue commandQueue;
	private JPanel attributePane;
	private JScrollPane scrollPane;
	private JTextField status;
	private Map<String, Control> parameterControls = new HashMap<String, Control>();

	public MainFrame(CommandQueue commandQueue) {
		super("Device Simulator");
		this.commandQueue = commandQueue;
		initLayout();
		initMenuBar();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initMenuBar() {
		JMenuBar mb = new JMenuBar();
		JMenu device = new JMenu("Device");
		JMenuItem loadNewDevice = new JMenuItem(new NewDeviceAction(this, commandQueue));
		JMenuItem connectToServer = new JMenuItem(new ConnectAction(this, commandQueue));
		device.add(loadNewDevice);
		device.add(connectToServer);
		JMenu send = new JMenu("Send");
		JMenuItem doReport = new JMenuItem(new ReportAction(commandQueue));
		send.add(doReport);
		mb.add(device);
		mb.add(send);
		setJMenuBar(mb);
	}

	private void initLayout() {
		setSize(800, 600);
		JPanel p = new JPanel();
		attributePane = new JPanel();
		scrollPane = new JScrollPane(attributePane);
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		status = new JTextField(132);
		status.setEditable(false);
		p.setLayout(new BorderLayout());
		p.add(scrollPane, BorderLayout.CENTER);
		p.add(status, BorderLayout.SOUTH);
		setContentPane(p);
	}

	public void showOnline(boolean isOnline) {
		status.setText(isOnline ? "ONLINE" : "DISCONNECTED");
	}

	public void createControls(DeviceModel deviceModel) {
		setTitle(deviceModel.getVendor() + ":" + deviceModel.getModel() + ":" + deviceModel.getSn());
		JComponent parent = attributePane;
		parent.removeAll();
		parent.setLayout(new BoxLayout(parent, BoxLayout.PAGE_AXIS));
		List<String> names = deviceModel.getParameterNames();
		for (String name : names) {
			ParameterDefinition paramDef = deviceModel.getParameter(name);
			if (paramDef.getType().equals("enum")) {
				String value = (String)paramDef.getCurrentValue(false);
				if (name.endsWith("contact")) {
					TwoStateControl contactControl = new TwoStateControl(parent,
							"ame_contactopen.png",
							"ame_contactclosed.png",
							"opened",
							"closed",
							name,
							value,
							commandQueue);			
					parameterControls.put(name, contactControl);
				}
				else if (name.endsWith("switch")) {
					TwoStateControl contactControl = new TwoStateControl(parent,
							"switch_on.png",
							"switch_off.png",
							"on",
							"off",
							name,
							value,
							commandQueue);			
					parameterControls.put(name, contactControl);
				}
				else {	
					EnumControl enumControl = new EnumControl(parent, name, paramDef.getEnumvalues(), 
							value, commandQueue);
					parameterControls.put(name, enumControl);
				}
			}
			else if (paramDef.getType().equals("number")) {
				double numberValue = 0;
				Object value = paramDef.getCurrentValue(false);
				if (value instanceof Double) {
					numberValue = (Double)value;
				}
				else {
					numberValue = Double.valueOf(value.toString());
				}
				double floor = paramDef.getFloor();
				double ceiling = paramDef.getCeiling();
				NumberControl numberControl = new NumberControl(parent, name, floor, ceiling, 
						numberValue, commandQueue);
				parameterControls.put(name, numberControl);
			}
		}
		Rectangle bounds = getBounds();
		setSize(bounds.width - 1,bounds.height);
	}

	public void updateControl(String name, Object value) {
		parameterControls.get(name).updateValue(value);
	}
}
