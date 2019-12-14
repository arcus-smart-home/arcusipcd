package com.arcussmarthome.ipcd.client.handler;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.GetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.GetParameterValuesResponse;
import com.arcussmarthome.ipcd.msg.Status;

public class GetParameterValuesHandler extends CommandHandler<GetParameterValuesCommand> {

	// for now pass the device model reference in
	public GetParameterValuesHandler(IpcdClientDevice client) {
		super(client);
	}

	@Override
	public void handleCommand(GetParameterValuesCommand command) {
		GetParameterValuesResponse resp = new GetParameterValuesResponse();
	
		resp.setDevice(getDevice());
		resp.setRequest(command);
		resp.setStatus(new Status());
		
		Map<String,Object> response = new LinkedHashMap<String,Object>();
		List<String> parameterNames = command.getParameters();
		
		// return all parameters if none were specified in the request
		if (parameterNames == null || parameterNames.isEmpty()) {
			parameterNames = client.getDeviceModel().getParameterNames();
		}
		for (String parameter: parameterNames) {
			response.put(parameter, client.getDeviceModel().getParameterValue(true, parameter));
		}
		resp.setResponse(response);

		client.sendMessage(resp, true);
	}
	
}
