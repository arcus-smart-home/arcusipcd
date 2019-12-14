package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

public class TestDownloadCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		DownloadCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-download-1.json")), DownloadCommand.class);
		
		assertEquals(CommandType.Download, command1.getCommand());
		assertEquals("https://thingyverse.com/images/devices/BlackBox/ms2/1.20-b236.bin", command1.getUrl());
		assertNull(command1.getUsername());
		assertNull(command1.getPassword());
	}
	
	@Test
	public void testSer() {
		
		DownloadCommand constructed = new DownloadCommand();
		constructed.setUrl("https://thingyverse.com/images/devices/BlackBox/ms2/1.20-b236.bin");
		
		String json = ser.toJson(constructed);
		DownloadCommand deserialized = ser.fromJson(json, DownloadCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}


}
