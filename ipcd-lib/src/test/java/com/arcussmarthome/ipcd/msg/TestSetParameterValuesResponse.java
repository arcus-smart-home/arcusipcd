package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestSetParameterValuesResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		SetParameterValuesResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-setparametervalues-1.json")), SetParameterValuesResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.SetParameterValues, response1.getRequest().getCommand());
		assertNotNull(response1.getRequest().getValues());
		assertEquals(2, response1.getRequest().getValues().size());
		assertEquals(true, response1.getRequest().getValues().get("ms2.lightsensorenabled"));
		assertEquals("celcius", response1.getRequest().getValues().get("ms2.temperaturescale"));
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		assertNotNull(response1.getResponse());
		assertEquals(2, response1.getResponse().size());
		assertEquals(true, response1.getResponse().get("ms2.lightsensorenabled"));
		assertEquals("celcius", response1.getResponse().get("ms2.temperaturescale"));
	}
	
	@Test
	public void testSer() {
		
		SetParameterValuesResponse constructed = new SetParameterValuesResponse();
		
		SetParameterValuesCommand command = new SetParameterValuesCommand();
		Map<String,Object> values = new HashMap<String,Object>();
		values.put("ms2.lightsensorenabled", true);
		values.put("ms2.temperaturescale", "celcius");
		command.setValues(values);
		
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("ms2.lightsensorenabled", true);
		response.put("ms2.temperaturescale", "celcius");
		
		constructed.setDevice(device);
		constructed.setRequest(command);
		constructed.setStatus(status);
		constructed.setResponse(response);
		
		String json = ser.toJson(constructed);
		SetParameterValuesResponse deserialized = ser.fromJson(json, SetParameterValuesResponse.class);
		
		assertEquals(constructed, deserialized);
	}


}
