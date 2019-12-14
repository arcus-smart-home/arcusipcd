package com.arcussmarthome.ipcd.ser;

import java.lang.reflect.Type;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.arcussmarthome.ipcd.msg.DownloadCommand;

public class DownloadCommandSerializer implements JsonSerializer<DownloadCommand> {
	
	public JsonElement serialize(DownloadCommand cmd, Type typeOfSrc, JsonSerializationContext context) {
		final JsonObject object = new JsonObject();
		object.add("command", context.serialize(cmd.getCommand()));
		if (cmd.getTxnid() != null) object.add("txnid", context.serialize(cmd.getTxnid()));
		
		if (cmd.getUrl() != null) object.add("url", context.serialize(cmd.getUrl()));
		if (cmd.getUsername() != null) object.add("user", context.serialize(cmd.getUsername()));
		if (cmd.getPassword() != null) object.add("password", context.serialize(cmd.getPassword()));
		
		return object;
	}
	
}
