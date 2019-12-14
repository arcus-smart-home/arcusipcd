package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestSetParameterValuesCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		SetParameterValuesCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-setparametervalues-1.json")), SetParameterValuesCommand.class);
		assertEquals(CommandType.SetParameterValues, command1.getCommand());
		assertNotNull(command1.getValues());
		assertEquals(2, command1.getValues().size());
		assertEquals(true, command1.getValues().get("ms2.lightsensorenabled"));
		assertEquals("celcius", command1.getValues().get("ms2.temperaturescale"));
	}
	
	@Test
	public void testSer() {
		
		SetParameterValuesCommand constructed = new SetParameterValuesCommand();
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("ms2.lightsensorenabled", true);
		values.put("ms2.temperaturescale", "celcius");
		constructed.setValues(values);
		
		String json = ser.toJson(constructed);
		SetParameterValuesCommand deserialized = ser.fromJson(json, SetParameterValuesCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}

}
