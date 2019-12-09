package com.arcussmarthome.ipcd.ser;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.arcussmarthome.ipcd.msg.GetDeviceInfoCommand;

public class GetDeviceInfoCommandSerializer implements JsonSerializer<GetDeviceInfoCommand> {

	@Override
	public JsonElement serialize(GetDeviceInfoCommand cmd, Type typeOfSrc,
			JsonSerializationContext context) {
		final JsonObject object = new JsonObject();
		object.add("command", context.serialize(cmd.getCommand()));
		if (cmd.getTxnid() != null) object.add("txnid", context.serialize(cmd.getTxnid()));
		return object;
	}

}
