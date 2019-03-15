package com.iris.ipcd.client.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import com.iris.ipcd.client.comm.IpcdClientDevice;
import com.iris.ipcd.client.model.ValidationException;
import com.iris.ipcd.msg.SetParameterValuesCommand;
import com.iris.ipcd.msg.SetParameterValuesResponse;
import com.iris.ipcd.msg.Status;
import com.iris.ipcd.msg.Status.Result;

public class SetParameterValuesHandler extends CommandHandler<SetParameterValuesCommand> {

	public SetParameterValuesHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(SetParameterValuesCommand command) {
		SetParameterValuesResponse resp = new SetParameterValuesResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		
		Status status = new Status();
		// set the parameters and determine success or failure based on 
		// parameter validation exceptions
		for (String parameter: command.getValues().keySet()) {
			try {
				client.setParameterValue(parameter, command.getValues().get(parameter));
			} catch (ValidationException e) {
				status.setResult(Result.fail);
				status.getMessages().add(e.getLocalizedMessage());
			}
		}
		resp.setStatus(status);

		// build response map
		Map<String,Object> response = new LinkedHashMap<String,Object>();
		for (String parameter: command.getValues().keySet()) {
			response.put(parameter, client.getDeviceModel().getParameterValue(false, parameter));
		}
		resp.setResponse(response);
		
		client.sendMessage(resp, false);
	
	}

	
}
