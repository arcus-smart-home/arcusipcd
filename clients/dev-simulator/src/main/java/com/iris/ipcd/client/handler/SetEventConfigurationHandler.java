package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.client.model.ValidationException;
import com.iris.ipcd.msg.SetEventConfigurationCommand;
import com.iris.ipcd.msg.SetEventConfigurationResponse;
import com.iris.ipcd.msg.Status;
import com.iris.ipcd.msg.Status.Result;

public class SetEventConfigurationHandler extends CommandHandler<SetEventConfigurationCommand> {

	
	public SetEventConfigurationHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(SetEventConfigurationCommand command) {
		SetEventConfigurationResponse resp = new SetEventConfigurationResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		
		Status status = new Status();
		// set enabled events
		try {
			client.getDeviceModel().setEnabledEvents(command.getEnabledEvents());
		} catch (ValidationException e) {
			status.setResult(Result.fail);
			status.getMessages().add(e.getLocalizedMessage());
		}
		// set enabled value changes
		client.getDeviceModel().clearParameterEnabledValueChanges();
		for (String parameter: command.getEnabledValueChanges().keySet()) {
			try {
				client.getDeviceModel().setParameterEnabledValueChanges(parameter, command.getEnabledValueChanges().get(parameter));
			} catch (ValidationException e) {
				status.setResult(Result.fail);
				status.getMessages().add(e.getLocalizedMessage());
			}
		}
		resp.setStatus(status);
		resp.setResponse(client.getDeviceModel().getSetEventConfiguration());
		
		client.sendMessage(resp, false);
	}
}
