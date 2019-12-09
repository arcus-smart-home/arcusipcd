package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

public class TestGetParameterInfoCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetParameterInfoCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-getparameterinfo-1.json")), GetParameterInfoCommand.class);
	
		assertEquals(CommandType.GetParameterInfo, command1.getCommand());
	}
	
	@Test
	public void testSer() {
		
		GetParameterInfoCommand constructed = new GetParameterInfoCommand();
		
		String json = ser.toJson(constructed);
		GetParameterInfoCommand deserialized = ser.fromJson(json, GetParameterInfoCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}

}
