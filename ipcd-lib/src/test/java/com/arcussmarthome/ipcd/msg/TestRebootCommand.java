package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

public class TestRebootCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		RebootCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-reboot-1.json")), RebootCommand.class);
		
		assertEquals(CommandType.Reboot, command1.getCommand());
	}
	
	@Test
	public void testSer() {
		
		RebootCommand constructed = new RebootCommand();
		
		String json = ser.toJson(constructed);
		RebootCommand deserialized = ser.fromJson(json, RebootCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}

}
