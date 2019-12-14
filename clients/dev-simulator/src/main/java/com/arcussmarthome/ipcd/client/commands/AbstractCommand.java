package com.arcussmarthome.ipcd.client.commands;

import java.util.HashMap;
import java.util.Map;

public abstract class AbstractCommand implements Command {
	
	private final Map<String, String> attributes = new HashMap<String, String>();

	@Override
	public Map<String, String> getAttributes() {
		return attributes;
	}

	@Override
	public void putAttribute(String attr, String value) {
		attributes.put(attr, value);
	}

}
