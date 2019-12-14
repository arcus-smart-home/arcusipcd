package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestGetParameterInfoResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetParameterInfoResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-getparameterinfo-1.json")), GetParameterInfoResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.GetParameterInfo, response1.getRequest().getCommand());
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		assertNotNull(response1.getResponse());
		assertEquals(6, response1.getResponse().size());
		
		
		ParameterInfo p = response1.getResponse().get("ms2.temperature");
		assertEquals("number", p.getType());
		assertEquals(ParameterAttrib.readonly, p.getAttrib());
		assertEquals("Temperature in degrees.  Scale is either Celcius or Farenheit based on the value of the ms2.temperaturescale parameter", p.getDescription());
		
		
		p = response1.getResponse().get("ms2.humidity");
		assertEquals("number", p.getType());
		assertEquals(ParameterAttrib.readonly, p.getAttrib());
		assertEquals("percent", p.getUnit());
		assertEquals(new Double(0.0), p.getFloor());
		assertEquals(new Double(100.0), p.getCeiling());
		assertEquals("Relative humidity as a percentage from 0 to 100", p.getDescription());
		
		p = response1.getResponse().get("ms2.luminosity");
		assertEquals("number", p.getType());
		assertEquals(ParameterAttrib.readonly, p.getAttrib());
		assertEquals("lux", p.getUnit());
		assertEquals(new Double(1.0), p.getFloor());
		assertEquals(new Double(100000.0), p.getCeiling());
		assertEquals("Illuminance in lux", p.getDescription());

		p = response1.getResponse().get("ms2.batterylevel");
		assertEquals("number", p.getType());
		assertEquals(ParameterAttrib.readonly, p.getAttrib());
		assertEquals("percent", p.getUnit());
		assertEquals(new Double(1.0), p.getFloor());
		assertEquals(new Double(100.0), p.getCeiling());
		assertEquals("Approximate percentage of battery power remaining in units of 10", p.getDescription());
		
		p = response1.getResponse().get("ms2.lightsensorenabled");
		assertEquals("enum", p.getType());
		assertEquals(ParameterAttrib.readwrite, p.getAttrib());
		assertNotNull(p.getEnumvalues());
		assertEquals(2, p.getEnumvalues().size());
		assertTrue(p.getEnumvalues().contains("on"));
		assertTrue(p.getEnumvalues().contains("off"));
		assertEquals("When on, the light sensor will be enabled and illuminance measurements will be reported.  When off, ms2.luminosity will always return 0", p.getDescription());
		
		p = response1.getResponse().get("ms2.temperaturescale");
		assertEquals("enum", p.getType());
		assertEquals(ParameterAttrib.readwrite, p.getAttrib());
		assertNotNull(p.getEnumvalues());
		assertEquals(2, p.getEnumvalues().size());
		assertTrue(p.getEnumvalues().contains("celcius"));
		assertTrue(p.getEnumvalues().contains("farenheit"));
		assertEquals("The scale of the ms2.temperature attribute", p.getDescription());
		
	}
	
	@Test
	public void testSer() {
		
		GetParameterInfoResponse constructed = new GetParameterInfoResponse();
		
		GetParameterInfoCommand command = new GetParameterInfoCommand();
		
		Map<String,ParameterInfo> response = new HashMap<String,ParameterInfo>();
		ParameterInfo p1 = new ParameterInfo();
		p1.setType("number");
		p1.setAttrib(ParameterAttrib.readonly);
		p1.setDescription("Temperature in degrees.  Scale is either Celcius or Farenheit based on the value of the ms2.temperaturescale parameter");
		response.put("ms2.temperature", p1);

		ParameterInfo p2 = new ParameterInfo();
		p2.setType("number");
		p2.setAttrib(ParameterAttrib.readonly);
		p2.setUnit("percent");
		p2.setFloor(0.0);
		p2.setCeiling(100.0);
		p2.setDescription("Relative humidity as a percentage from 0 to 100");
		response.put("ms2.humidity", p2);
		
		ParameterInfo p3 = new ParameterInfo();
		p3.setType("number");
		p3.setAttrib(ParameterAttrib.readonly);
		p3.setUnit("lux");
		p3.setFloor(1.0);
		p3.setCeiling(100000.0);
		p3.setDescription("Illuminance in lux");
		response.put("ms2.luminosity", p3);
		
		ParameterInfo p4 = new ParameterInfo();
		p4.setType("number");
		p4.setAttrib(ParameterAttrib.readonly);
		p4.setUnit("percent");
		p4.setFloor(1.0);
		p4.setCeiling(100.0);
		p4.setDescription("Approximate percentage of battery power remaining in units of 10");
		response.put("ms2.batterylevel", p4);
		
		ParameterInfo p5 = new ParameterInfo();
		p5.setType("enum");
		p5.setEnumvalues(Arrays.asList("on", "off"));
		p5.setAttrib(ParameterAttrib.readwrite);
		p5.setDescription("When on, the light sensor will be enabled and illuminance measurements will be reported.  When off, ms2.luminosity will always return 0");
		response.put("ms2.lightsensorenabled", p5);
		
		ParameterInfo p6 = new ParameterInfo();
		p6.setType("enum");
		p6.setEnumvalues(Arrays.asList("celcius", "farenheit"));
		p6.setAttrib(ParameterAttrib.readwrite);
		p6.setDescription("The scale of the ms2.temperature attribute");
		response.put("ms2.temperaturescale", p6);
		
		constructed.setDevice(device);
		constructed.setRequest(command);
		constructed.setStatus(status);
		constructed.setResponse(response);
		
		String json = ser.toJson(constructed);
		GetParameterInfoResponse deserialized = ser.fromJson(json, GetParameterInfoResponse.class);
		
		assertEquals(constructed, deserialized);
	}

}
