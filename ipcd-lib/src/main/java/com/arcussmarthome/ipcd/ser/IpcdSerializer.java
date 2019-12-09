package com.arcussmarthome.ipcd.ser;

import java.io.Reader;
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.arcussmarthome.ipcd.msg.ClientMessage;
import com.arcussmarthome.ipcd.msg.CommandType;
import com.arcussmarthome.ipcd.msg.DownloadCommand;
import com.arcussmarthome.ipcd.msg.DownloadResponse;
import com.arcussmarthome.ipcd.msg.EventAction;
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
import com.arcussmarthome.ipcd.msg.LeaveCommand;
import com.arcussmarthome.ipcd.msg.LeaveResponse;
import com.arcussmarthome.ipcd.msg.RebootCommand;
import com.arcussmarthome.ipcd.msg.RebootResponse;
import com.arcussmarthome.ipcd.msg.ReportAction;
import com.arcussmarthome.ipcd.msg.ServerMessage;
import com.arcussmarthome.ipcd.msg.SetDeviceInfoCommand;
import com.arcussmarthome.ipcd.msg.SetDeviceInfoResponse;
import com.arcussmarthome.ipcd.msg.SetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.SetEventConfigurationResponse;
import com.arcussmarthome.ipcd.msg.SetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.SetParameterValuesResponse;
import com.arcussmarthome.ipcd.msg.SetReportConfigurationCommand;
import com.arcussmarthome.ipcd.msg.SetReportConfigurationResponse;

public class IpcdSerializer {

	private final Gson gson;
	private final Gson gsonSerializeNulls;
	
	public IpcdSerializer() {
		
		GsonBuilder builder = new GsonBuilder(); 
		
		// Type adapters to de/serialize Java Date as Json number
		builder.registerTypeAdapter(Date.class, new DateTypeAdapter());
		// Type adapters to serialize complex server commands in a predictable manner 
		// (with ordered message attributes) so that devices need not implement a full JSON parser
		builder.registerTypeAdapter(DownloadCommand.class, new DownloadCommandSerializer());
		builder.registerTypeAdapter(FactoryResetCommand.class, new FactoryResetCommandSerializer());
		builder.registerTypeAdapter(LeaveCommand.class, new LeaveCommandSerializer());
		builder.registerTypeAdapter(GetDeviceInfoCommand.class, new GetDeviceInfoCommandSerializer());
		builder.registerTypeAdapter(GetEventConfigurationCommand.class, new GetEventConfigurationCommandSerializer());
		builder.registerTypeAdapter(GetParameterInfoCommand.class, new GetParameterInfoCommandSerializer());
		builder.registerTypeAdapter(GetParameterValuesCommand.class, new GetParameterValuesCommandSerializer());
		builder.registerTypeAdapter(GetReportConfigurationCommand.class, new GetReportConfigurationCommandSerializer());
		builder.registerTypeAdapter(RebootCommand.class, new RebootCommandSerializer());
		builder.registerTypeAdapter(SetEventConfigurationCommand.class, new SetEventConfigurationCommandSerializer());
		builder.registerTypeAdapter(SetDeviceInfoCommand.class, new SetDeviceInfoCommandSerializer());
		builder.registerTypeAdapter(SetParameterValuesCommand.class, new SetParameterValuesCommandSerializer());
		builder.registerTypeAdapter(SetReportConfigurationCommand.class, new SetReportConfigurationCommandSerializer());		

		gson = builder.create();
		gsonSerializeNulls = builder.serializeNulls().create();
	}
	
	public <T> T fromJson(Reader json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	
	public <T> T fromJson(String json, Class<T> classOfT) {
		return gson.fromJson(json, classOfT);
	}
	
	public ServerMessage parseServerMessage(Reader json) throws InvalidMessageException {
		JsonParser p = new JsonParser();
        
		JsonObject envelope  = p.parse(json).getAsJsonObject();
        
		String command = envelope.get("command").getAsString();
		
		if (command != null) {
			CommandType type = CommandType.valueOf(command);
    		switch (type) {
        		case Download : return gson.fromJson(envelope, DownloadCommand.class);
        		case FactoryReset : return gson.fromJson(envelope, FactoryResetCommand.class);
        		case Leave: return gson.fromJson(envelope, LeaveCommand.class);
        		case Reboot : return gson.fromJson(envelope, RebootCommand.class);
        		case GetDeviceInfo : return gson.fromJson(envelope, GetDeviceInfoCommand.class);
        		case SetDeviceInfo : return gson.fromJson(envelope, SetDeviceInfoCommand.class);
        		case GetEventConfiguration : return gson.fromJson(envelope, GetEventConfigurationCommand.class);
        		case GetParameterInfo : return gson.fromJson(envelope, GetParameterInfoCommand.class);
        		case GetParameterValues : return gson.fromJson(envelope, GetParameterValuesCommand.class);
        		case GetReportConfiguration : return gson.fromJson(envelope, GetReportConfigurationCommand.class);
        		case SetEventConfiguration : return gson.fromJson(envelope, SetEventConfigurationCommand.class);
        		case SetParameterValues : return gson.fromJson(envelope, SetParameterValuesCommand.class);
        		case SetReportConfiguration : return gson.fromJson(envelope, SetReportConfigurationCommand.class);
    		}
		}
		
		throw new InvalidMessageException("Not a valid server message");
	}
	
	public ClientMessage parseClientMessage(Reader json) throws InvalidMessageException {
		JsonParser p = new JsonParser();
        
		JsonObject envelope  = p.parse(json).getAsJsonObject();
		JsonElement e = null;
        
        e = envelope.get("device");
        
        if (e != null) {
        	// must be a ClientMessage
        	
        	e = envelope.get("report");
        	if (e != null) {
        		return gson.fromJson(envelope, ReportAction.class);
        	}
        	
        	e = envelope.get("events");
        	if (e != null) {
        		return gson.fromJson(envelope, EventAction.class);
        	}
        	
        	e = envelope.get("request");
        	if (e != null) {
        		// check command
        		
        		String command = e.getAsJsonObject().get("command").getAsString();
        		CommandType type = CommandType.valueOf(command);
        		switch (type) {
	        		case Download : return gson.fromJson(envelope, DownloadResponse.class);
	        		case FactoryReset : return gson.fromJson(envelope, FactoryResetResponse.class);
	        		case Leave : return gson.fromJson(envelope, LeaveResponse.class);
	        		case Reboot : return gson.fromJson(envelope, RebootResponse.class);
	        		case GetDeviceInfo : return gson.fromJson(envelope, GetDeviceInfoResponse.class);
	        		case SetDeviceInfo : return gson.fromJson(envelope, SetDeviceInfoResponse.class);
	        		case GetEventConfiguration : return gson.fromJson(envelope, GetEventConfigurationResponse.class);
	        		case GetParameterInfo : return gson.fromJson(envelope, GetParameterInfoResponse.class);
	        		case GetParameterValues : return gson.fromJson(envelope, GetParameterValuesResponse.class);
	        		case GetReportConfiguration : return gson.fromJson(envelope, GetReportConfigurationResponse.class);
	        		case SetEventConfiguration : return gson.fromJson(envelope, SetEventConfigurationResponse.class);
	        		case SetParameterValues : return gson.fromJson(envelope, SetParameterValuesResponse.class);
	        		case SetReportConfiguration : return gson.fromJson(envelope, SetReportConfigurationResponse.class);
        		}
        	}
        		
        } 
    	throw new InvalidMessageException("Not a valid client message");
	}
	
	public String toJson(Object msg) {
		return gson.toJson(msg);
	}
	
	public String toJsonSerializeNulls(Object msg) {
		return gsonSerializeNulls.toJson(msg);
	}
}
