package com.arcussmarthome.ipcd.client.ux;

import java.awt.Dimension;

import javax.swing.Box;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.arcussmarthome.ipcd.client.commands.Command;
import com.arcussmarthome.ipcd.client.commands.CommandQueue;
import com.arcussmarthome.ipcd.client.commands.SetParameterValue;

public class NumberControl implements Control {
	private final SpinnerNumberModel model;
	private final JSpinner spinner;
	private boolean isUpdating = false;

	public NumberControl(JComponent parent, final String title, double floor, double ceiling,
			double number, final CommandQueue commandQueue) {
		
		JLabel label = new JLabel(title);
		parent.add(label);
		
		model = new SpinnerNumberModel(number, floor, ceiling, 1.0);
		spinner = new JSpinner(model);
		spinner.setMaximumSize(new Dimension(200,40));
		spinner.addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				if (!isUpdating) {
					Number newNumber = model.getNumber();
					Command setCommand = new SetParameterValue();
					setCommand.putAttribute(title, newNumber.toString());
					try {
						commandQueue.insertCommand(setCommand);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}
		});
		
		parent.add(spinner);
		parent.add(Box.createVerticalGlue());
	}

	@Override
	public void updateValue(Object obj) {
		isUpdating = true;
		if (obj instanceof String) {
			model.setValue(Double.valueOf((String)obj));
		}
		else {
			model.setValue(obj);
		}
		isUpdating = false;
	}
}
