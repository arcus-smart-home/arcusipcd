package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestGetReportConfigurationResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetReportConfigurationResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-getreportconfiguration-1.json")), GetReportConfigurationResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.GetReportConfiguration, response1.getRequest().getCommand());
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		ReportConfiguration r = response1.getResponse();
		assertNotNull(response1.getResponse());
		
		assertEquals(new Integer(900), r.getInterval());
		assertNotNull(r.getParameters());
		assertEquals(3, r.getParameters().size());
		assertTrue(r.getParameters().contains("ms2.temperature"));
		assertTrue(r.getParameters().contains("ms2.humidity"));
		assertTrue(r.getParameters().contains("ms2.luminosity"));
	
		
	}
	
	@Test
	public void testSer() {
		
		GetReportConfigurationResponse constructed = new GetReportConfigurationResponse();
		
		ReportConfiguration response = new ReportConfiguration();
		response.setInterval(900);
		response.setParameters(Arrays.asList("ms2.temperature", "ms2.humidity", "ms2.luminosity"));
		
		constructed.setDevice(device);
		constructed.setRequest(new GetReportConfigurationCommand());
		constructed.setStatus(status);
		constructed.setResponse(response);
		
		String json = ser.toJson(constructed);
		GetReportConfigurationResponse deserialized = ser.fromJson(json, GetReportConfigurationResponse.class);
		
		assertEquals(constructed, deserialized);
	}


}
