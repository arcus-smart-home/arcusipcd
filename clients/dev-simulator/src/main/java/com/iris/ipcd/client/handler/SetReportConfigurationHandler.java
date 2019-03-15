package com.iris.ipcd.client.handler;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.client.model.ValidationException;
import com.iris.ipcd.msg.SetReportConfigurationCommand;
import com.iris.ipcd.msg.SetReportConfigurationResponse;
import com.iris.ipcd.msg.Status;
import com.iris.ipcd.msg.Status.Result;

public class SetReportConfigurationHandler extends CommandHandler<SetReportConfigurationCommand> {

	public SetReportConfigurationHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(SetReportConfigurationCommand command) {
		SetReportConfigurationResponse resp = new SetReportConfigurationResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		
		Status status = new Status();
		// set the report configuration and determine success or failure based on 
		// validation exceptions
		try {
			if (command.getInterval() != null) {
				client.setReportInterval(command.getInterval());
			}
			if (command.getParameters() != null) {
				client.getDeviceModel().setReportParameterNames(command.getParameters());
			}
		} catch (ValidationException e) {
			status.setResult(Result.fail);
			status.getMessages().add(e.getLocalizedMessage());
		}
		resp.setStatus(status);
		resp.setResponse(client.getReportConfiguration());

		client.sendMessage(resp, false);
	}

}
