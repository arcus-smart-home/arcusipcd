package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestReportAction extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		ReportAction report1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("report-1.json")), ReportAction.class);
	
		assertNotNull(report1.getDevice());
		assertEquals("BlackBox", report1.getDevice().getVendor());
		assertEquals("Multisensor2", report1.getDevice().getModel());
		assertEquals("00049B3C7A05", report1.getDevice().getSn());
		assertEquals("1.0", report1.getDevice().getIpcdver());
		
		assertNotNull(report1.getReport());
		assertTrue(report1.getReport().keySet().size() == 3);
		assertEquals(22.8, report1.getReport().get("ms2.temperature"));
		assertEquals(34.2, report1.getReport().get("ms2.humidity"));
		assertEquals(10270.0, report1.getReport().get("ms2.luminosity"));
		
	}
	
	@Test
	public void testSer() {
		
		Map<String,Object> paramValues = new HashMap<String,Object>();
		paramValues.put("ms2.temperature", 22.8);
		paramValues.put("ms2.humidity", 34.2);
		paramValues.put("ms2.luminosity", 10270.0);
		
		ReportAction constructed = new ReportAction();
		constructed.setDevice(device);
		constructed.setReport(paramValues);
		
		String json = ser.toJson(constructed);
		ReportAction deserialized = ser.fromJson(json, ReportAction.class);
		
		assertEquals(constructed, deserialized);
	}
	

}
