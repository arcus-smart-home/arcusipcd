package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;


public class TestGetDeviceInfoCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetDeviceInfoCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-getdeviceinfo-1.json")), GetDeviceInfoCommand.class);
	
		assertEquals(CommandType.GetDeviceInfo, command1.getCommand());
	}
	
	@Test
	public void testSer() {
		
		GetDeviceInfoCommand constructed = new GetDeviceInfoCommand();
		
		String json = ser.toJson(constructed);
		GetDeviceInfoCommand deserialized = ser.fromJson(json, GetDeviceInfoCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}

}
