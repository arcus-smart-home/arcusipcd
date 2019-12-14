package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestGetParameterValuesResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetParameterValuesResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-getparametervalues-1.json")), GetParameterValuesResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.GetParameterValues, response1.getRequest().getCommand());
		List<String> params = response1.getRequest().getParameters();
		assertNotNull(params);
		assertEquals(3, params.size());
		assertTrue(params.contains("ms2.temperature"));
		assertTrue(params.contains("ms2.humidity"));
		assertTrue(params.contains("ms2.luminosity"));
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		assertNotNull(response1.getResponse());
		assertEquals(3, response1.getResponse().size());
		assertEquals(22.8, response1.getResponse().get("ms2.temperature"));
		assertEquals(34.2, response1.getResponse().get("ms2.humidity"));
		assertEquals(10270.0, response1.getResponse().get("ms2.luminosity"));
	}
	
	@Test
	public void testSer() {
		
		GetParameterValuesResponse constructed = new GetParameterValuesResponse();
		
		GetParameterValuesCommand command = new GetParameterValuesCommand();
		command.setParameters(Arrays.asList("ms2.temperature", "ms2.humidity", "ms2.luminosity"));
		
		Map<String,Object> response = new HashMap<String,Object>();
		response.put("ms2.temperature", 22.8);
		response.put("ms2.humidity", 34.2);
		response.put("ms2.luminosity", 10270.0);
		
		constructed.setDevice(device);
		constructed.setRequest(command);
		constructed.setStatus(status);
		constructed.setResponse(response);
		
		String json = ser.toJson(constructed);
		GetParameterValuesResponse deserialized = ser.fromJson(json, GetParameterValuesResponse.class);
		
		assertEquals(constructed, deserialized);
	}

}
