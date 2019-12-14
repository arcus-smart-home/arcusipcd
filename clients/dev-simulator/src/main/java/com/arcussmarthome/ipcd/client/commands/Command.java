package com.arcussmarthome.ipcd.client.commands;

import java.util.Map;

public interface Command {
	Commands getType();
	Map<String, String> getAttributes();
	void putAttribute(String attr, String value);
}
