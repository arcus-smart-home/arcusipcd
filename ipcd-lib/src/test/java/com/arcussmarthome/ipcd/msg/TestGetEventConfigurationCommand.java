package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

public class TestGetEventConfigurationCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetEventConfigurationCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-geteventconfiguration-1.json")), GetEventConfigurationCommand.class);
	
		assertEquals(CommandType.GetEventConfiguration, command1.getCommand());
		assertEquals("316ce17a-9748-4713-9b07-951c34eb12ab", command1.getTxnid());
	}
	
	@Test
	public void testSer() {
		
		GetEventConfigurationCommand constructed = new GetEventConfigurationCommand();
		
		String json = ser.toJson(constructed);
		GetEventConfigurationCommand deserialized = ser.fromJson(json, GetEventConfigurationCommand.class);
		
		assertEquals(constructed, deserialized);
		
		assertEquals(constructed.getCommand(), deserialized.getCommand());
		assertEquals(constructed.getTxnid(), deserialized.getTxnid());
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
		
	}

}
