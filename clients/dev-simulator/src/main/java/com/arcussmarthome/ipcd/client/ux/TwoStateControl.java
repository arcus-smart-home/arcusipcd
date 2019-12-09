package com.arcussmarthome.ipcd.client.ux;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JLabel;

import com.arcussmarthome.ipcd.client.commands.Command;
import com.arcussmarthome.ipcd.client.commands.CommandQueue;
import com.arcussmarthome.ipcd.client.commands.SetParameterValue;

public class TwoStateControl implements Control {
	private boolean isStateOne = false;
	private final JLabel twoStateIcon;	
	private final ImageIcon stateOneImage;
	private final ImageIcon stateTwoImage;	
	private final String stateOneValue;
	private final String stateTwoValue;
	
	public TwoStateControl(JComponent parent, 
			String stateOneImageName,
			String stateTwoImageName,
			String stateOneString,
			String stateTwoString,
			final String title, 
			String value,
			final CommandQueue commandQueue) {
		
		stateOneImage = loadIcon(stateOneImageName);
		stateTwoImage = loadIcon(stateTwoImageName);
		
		this.stateOneValue = stateOneString;
		this.stateTwoValue = stateTwoString;
		
		JLabel label = new JLabel(title);
		parent.add(label);
		isStateOne = this.stateOneValue.equals(value);
		twoStateIcon = new JLabel(isStateOne ? stateOneImage : stateTwoImage);
		twoStateIcon.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Command setCommand = new SetParameterValue();
				if (isStateOne) {
					setState(stateTwoValue);
					setCommand.putAttribute(title, stateTwoValue);
				}
				else {
					setState(stateOneValue);
					setCommand.putAttribute(title, stateOneValue);
				}
				try {
					commandQueue.insertCommand(setCommand);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}

			@Override
			public void mousePressed(MouseEvent e) {}

			@Override
			public void mouseReleased(MouseEvent e) {}

			@Override
			public void mouseEntered(MouseEvent e) {}

			@Override
			public void mouseExited(MouseEvent e) {}
			
		});
		parent.add(twoStateIcon);
		parent.add(Box.createVerticalStrut(40));
	}	
	
	@Override
	public void updateValue(Object obj) {
		setState(obj);
	}
	
	private void setState(Object obj) {
		isStateOne = stateOneValue.equals(obj);
		twoStateIcon.setIcon(isStateOne ? stateOneImage : stateTwoImage);
	}
	
	private ImageIcon loadIcon(String name) {
		BufferedImage image;
		try {
			URL location = this.getClass().getResource("/images/" + name);
			image = ImageIO.read(location);
			return new ImageIcon(image);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
}
