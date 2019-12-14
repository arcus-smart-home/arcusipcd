package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;

import org.junit.Test;

public class TestSetReportConfigurationCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		SetReportConfigurationCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-setreportconfiguration-1.json")), SetReportConfigurationCommand.class);
		assertEquals(CommandType.SetReportConfiguration, command1.getCommand());
		assertEquals(new Integer(1800), command1.getInterval());
		assertNotNull(command1.getParameters());
		assertTrue(command1.getParameters().contains("ms2.temperature"));
		assertTrue(command1.getParameters().contains("ms2.humidity"));
	}
	
	
	@Test
	public void testSer() {
		SetReportConfigurationCommand constructed = new SetReportConfigurationCommand();
		constructed.setInterval(1800);
		constructed.setParameters(Arrays.asList("ms2.temperature", "ms2.humidity"));
		
		String json = ser.toJson(constructed);
		SetReportConfigurationCommand deserialized = ser.fromJson(json, SetReportConfigurationCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}
	
	
}
