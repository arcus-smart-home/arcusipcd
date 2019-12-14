package com.arcussmarthome.ipcd.ser;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.arcussmarthome.ipcd.msg.SetEventConfigurationCommand;

public class SetEventConfigurationCommandSerializer implements JsonSerializer<SetEventConfigurationCommand>{

	public JsonElement serialize(SetEventConfigurationCommand cmd, Type typeOfSrc, JsonSerializationContext context) {
		final JsonObject object = new JsonObject();
		object.add("command", context.serialize(cmd.getCommand()));
		if (cmd.getTxnid() != null) object.add("txnid", context.serialize(cmd.getTxnid()));

		if (cmd.getEnabledEvents() != null) object.add("enabledEvents", context.serialize(cmd.getEnabledEvents()));
		if (cmd.getEnabledValueChanges() != null) object.add("enabledValueChanges", context.serialize(cmd.getEnabledValueChanges()));
		
		return object;
	}


	
	
	
}
