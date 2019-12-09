package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestGetParameterValuesCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetParameterValuesCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-getparametervalues-1.json")), GetParameterValuesCommand.class);
	
		assertEquals(CommandType.GetParameterValues, command1.getCommand());
	}
	
	@Test
	public void testSer() {
		
		GetParameterValuesCommand constructed = new GetParameterValuesCommand();
		List<String> params = new ArrayList<String>();
		params.add("ms2.temperature");
		params.add("ms2.humidity");
		params.add("ms2.luminosity");
		constructed.setParameters(params);
		
		String json = ser.toJson(constructed);
		GetParameterValuesCommand deserialized = ser.fromJson(json, GetParameterValuesCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}

}
