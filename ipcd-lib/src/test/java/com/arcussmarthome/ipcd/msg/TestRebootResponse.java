package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestRebootResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		RebootResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-reboot-1.json")), RebootResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.Reboot, response1.getRequest().getCommand());
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		assertNotNull(response1.getResponse());
		assertEquals(0, response1.getResponse().size());
		
	}
	
	@Test
	public void testSer() {
		
		RebootResponse constructed = new RebootResponse();
		
		RebootCommand request = new RebootCommand();
		
		constructed.setDevice(device);
		constructed.setRequest(request);
		constructed.setStatus(status);
		
		String json = ser.toJson(constructed);
		RebootResponse deserialized = ser.fromJson(json, RebootResponse.class);
		
		assertEquals(constructed, deserialized);
	}

}