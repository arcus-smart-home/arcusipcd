package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

public class TestGetReportConfigurationCommand extends SerializerTest {
	
	@Test
	public void testDeser() throws Exception {
		
		GetReportConfigurationCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-getreportconfiguration-1.json")), GetReportConfigurationCommand.class);
	
		assertEquals(CommandType.GetReportConfiguration, command1.getCommand());
	}
	
	@Test
	public void testSer() {
		
		GetReportConfigurationCommand constructed = new GetReportConfigurationCommand();
		
		String json = ser.toJson(constructed);
		GetReportConfigurationCommand deserialized = ser.fromJson(json, GetReportConfigurationCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}
}
