package com.iris.ipcd.client.ux;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.iris.ipcd.client.commands.Command;
import com.iris.ipcd.client.commands.CommandQueue;
import com.iris.ipcd.client.commands.SetParameterValue;

public class EnumControl implements Control {
	
	private JComboBox<String> comboBox = null;
	private boolean isUpdating = false;

	public EnumControl(JComponent parent, 
			final String title, List<String> values, String current, final CommandQueue commandQueue) {
		JLabel label = new JLabel(title);
		parent.add(label);
		parent.add(Box.createVerticalGlue());
		
		comboBox = new JComboBox<String>();
		for (String value : values) {
			comboBox.addItem(value);
		}
		comboBox.setSelectedItem(current);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (!isUpdating) {
					String newValue = (String)comboBox.getSelectedItem();
					Command setCommand = new SetParameterValue();
					setCommand.putAttribute(title, newValue);
					try {
						commandQueue.insertCommand(setCommand);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
			
		});
		
		parent.add(comboBox);
		parent.add(Box.createVerticalStrut(40));
		parent.revalidate();
	}

	@Override
	public void updateValue(Object obj) {
		isUpdating = true;
		comboBox.setSelectedItem(obj);
		isUpdating = false;
	}
}
