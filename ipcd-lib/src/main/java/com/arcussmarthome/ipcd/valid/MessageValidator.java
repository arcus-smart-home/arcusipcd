package com.arcussmarthome.ipcd.valid;

import com.arcussmarthome.ipcd.msg.Action;
import com.arcussmarthome.ipcd.msg.Device;
import com.arcussmarthome.ipcd.msg.DownloadCommand;
import com.arcussmarthome.ipcd.msg.DownloadResponse;
import com.arcussmarthome.ipcd.msg.FactoryResetCommand;
import com.arcussmarthome.ipcd.msg.FactoryResetResponse;
import com.arcussmarthome.ipcd.msg.GetDeviceInfoCommand;
import com.arcussmarthome.ipcd.msg.GetDeviceInfoResponse;
import com.arcussmarthome.ipcd.msg.GetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.GetEventConfigurationResponse;
import com.arcussmarthome.ipcd.msg.GetParameterInfoCommand;
import com.arcussmarthome.ipcd.msg.GetParameterInfoResponse;
import com.arcussmarthome.ipcd.msg.GetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.GetParameterValuesResponse;
import com.arcussmarthome.ipcd.msg.GetReportConfigurationCommand;
import com.arcussmarthome.ipcd.msg.GetReportConfigurationResponse;
import com.arcussmarthome.ipcd.msg.RebootCommand;
import com.arcussmarthome.ipcd.msg.RebootResponse;
import com.arcussmarthome.ipcd.msg.Response;
import com.arcussmarthome.ipcd.msg.ServerMessage;
import com.arcussmarthome.ipcd.msg.SetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.SetEventConfigurationResponse;
import com.arcussmarthome.ipcd.msg.SetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.SetParameterValuesResponse;
import com.arcussmarthome.ipcd.msg.SetReportConfigurationCommand;
import com.arcussmarthome.ipcd.msg.SetReportConfigurationResponse;


/**
 * MessageValidator checks that a message has the expected structure and returns
 * warninns and errors in a ValidationResult object if any expectations are 
 * not met.
 * <p/>
 * Responses can be validated with:<br/>
 * ValidationResults r = new MessageValidator().withLastCommand(command).validate(response)
 * <p/>
 * Commands can be validated with:<br/>
 * ValidationResults r = new MessageValidator().validate(command)
 * <p/>
 * Actions can be validated with:<br/>
 * ValidationResults r = new MessageValidator().validate(action)
 * 
 * @author sperry
 *
 */
public class MessageValidator {

	private ServerMessage lastCommand;
	
	public MessageValidator withLastCommand(ServerMessage lastCommand) {
		this.lastCommand = lastCommand;
		return this;
	}	
	
	public ValidationResults validate(ServerMessage msg) {
		throw new RuntimeException("Not Implemented");
	}
	
	public ValidationResults validate(Action action) {
		throw new RuntimeException("Not Implemented");
	}
	
	public ValidationResults validate(Response<?> response) {
		ValidationResults v = new ValidationResults();
		
		// verify that command matches last command
		if (lastCommand != null) {
			
			// verify that response contains last command
			if (!lastCommand.equals(response.getRequest())) {
				v.addError("Command in response does not match last command " + lastCommand + ", " + response.getRequest());
			}
		}
		
		// verify that response is appropriate given type 
		Class<?> responseClass = response.getClass();
		Class<?> requestClass = response.getRequest().getClass();
		
		if (requestClass.equals(SetParameterValuesCommand.class)) {
			verifyExpectedResponse(SetParameterValuesResponse.class, responseClass, v);
		} else if (requestClass.equals(GetParameterValuesCommand.class)) {
			verifyExpectedResponse(GetParameterValuesResponse.class, responseClass, v);
		} else if (requestClass.equals(SetReportConfigurationCommand.class)) {
			verifyExpectedResponse(SetReportConfigurationResponse.class, responseClass, v);
		} else if (requestClass.equals(SetEventConfigurationCommand.class)) {
			verifyExpectedResponse(SetEventConfigurationResponse.class, responseClass, v);
		} else if (requestClass.equals(GetEventConfigurationCommand.class)) {
			verifyExpectedResponse(GetEventConfigurationResponse.class, responseClass, v);
		} else if (requestClass.equals(GetReportConfigurationCommand.class)) {
			verifyExpectedResponse(GetReportConfigurationResponse.class, responseClass, v);
		} else if (requestClass.equals(GetParameterInfoCommand.class)) {
			verifyExpectedResponse(GetParameterInfoResponse.class, responseClass, v);
		} else if (requestClass.equals(GetDeviceInfoCommand.class)) {
			verifyExpectedResponse(GetDeviceInfoResponse.class, responseClass, v);
		} else if (requestClass.equals(DownloadCommand.class)) {
			verifyExpectedResponse(DownloadResponse.class, responseClass, v);
		} else if (requestClass.equals(RebootCommand.class)) {
			verifyExpectedResponse(RebootResponse.class, responseClass, v);
		} else if (requestClass.equals(FactoryResetCommand.class)) {
			verifyExpectedResponse(FactoryResetResponse.class, responseClass, v);
		}
		
		// verify device
		verifyDevice(response.getDevice(), v);
		
		return v;
	}
	
	private void verifyExpectedResponse(Class<?> expected, Class<?> actual, ValidationResults v) {
		if (!(actual.equals(expected))) {
			v.addError("Expected " + expected.getSimpleName() + " but received " + actual.getSimpleName());
		}
	}
	
	private void verifyDevice(Device d, ValidationResults v) {
		if (d == null) {
			v.addError("Expected device in response was null");
		} else {
			if (d.getVendor() == null) {
				v.addError("Device is missing required vendor field");
			} 
			if (d.getModel() == null) {
				v.addError("Device is missing required model field");
			}
			if (d.getSn() == null) {
				v.addError("Device is missing required sn field");
			}
			if (d.getIpcdver() == null) {
				v.addError("Device is missing required ipcdver field");
			}
		}
	}
}
