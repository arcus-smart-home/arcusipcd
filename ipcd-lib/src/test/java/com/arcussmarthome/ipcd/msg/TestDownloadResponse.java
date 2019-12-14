package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestDownloadResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		DownloadResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-download-1.json")), DownloadResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.Download, response1.getRequest().getCommand());
		assertEquals("https://thingyverse.com/images/devices/BlackBox/ms2/1.20-b236.bin", response1.getRequest().getUrl());
		assertNull(response1.getRequest().getUsername());
		assertNull(response1.getRequest().getPassword());
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		assertNotNull(response1.getResponse());
		assertEquals(0, response1.getResponse().size());
		
	}
	
	@Test
	public void testSer() {
		
		DownloadResponse constructed = new DownloadResponse();
		
		DownloadCommand request = new DownloadCommand();
		request.setUrl("https://thingyverse.com/images/devices/BlackBox/ms2/1.20-b236.bin");
		
		constructed.setDevice(device);
		constructed.setRequest(request);
		constructed.setStatus(status);
		
		String json = ser.toJson(constructed);
		DownloadResponse deserialized = ser.fromJson(json, DownloadResponse.class);
		
		assertEquals(constructed, deserialized);
	}
}
