package com.arcussmarthome.ipcd.client.handler;

import java.util.LinkedHashMap;
import java.util.Map;

import com.arcussmarthome.ipcd.client.comm.IpcdClientDevice;
import com.arcussmarthome.ipcd.msg.SetDeviceInfoCommand;
import com.arcussmarthome.ipcd.msg.SetDeviceInfoResponse;
import com.arcussmarthome.ipcd.msg.Status;
import com.arcussmarthome.ipcd.msg.Status.Result;

public class SetDeviceInfoHandler extends CommandHandler<SetDeviceInfoCommand> {

	public SetDeviceInfoHandler(IpcdClientDevice client) {
		super(client);
	}
	
	@Override
	public void handleCommand(SetDeviceInfoCommand command) {
		
		SetDeviceInfoResponse resp = new SetDeviceInfoResponse();
		
		resp.setDevice(getDevice());
		resp.setRequest(command);
		
		Status status = new Status();
		
		boolean needToReconnect = false;
		
		for (String field: command.getValues().keySet()) {
			if ("connectUrl".equalsIgnoreCase(field)) {
				String newConnectUrl = command.getValues().get(field).toString();
				if (!client.getDeviceModel().getConnectUrl().equals(newConnectUrl)) {
					client.getDeviceModel().setConnectUrl(newConnectUrl);
					needToReconnect = true;
				}
			}
			else {
				status.setResult(Result.fail);
				status.getMessages().add("No writes are allowed for field " + field);
			}
		}
		resp.setStatus(status);
		
		Map<String, Object> response = new LinkedHashMap<String, Object>();
		for (String field: command.getValues().keySet()) {
			response.put(field, client.getDeviceInfoField(field));
		}
		resp.setResponse(response);
		
		client.sendMessage(resp, false);
		
		if (needToReconnect) {
			client.disconnect();
			try {
				client.connect();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
