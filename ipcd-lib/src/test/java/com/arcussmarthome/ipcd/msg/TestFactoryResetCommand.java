package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

public class TestFactoryResetCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		FactoryResetCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-factoryreset-1.json")), FactoryResetCommand.class);
		
		assertEquals(CommandType.FactoryReset, command1.getCommand());
	}
	
	@Test
	public void testSer() {
		
		FactoryResetCommand constructed = new FactoryResetCommand();
		
		String json = ser.toJson(constructed);
		FactoryResetCommand deserialized = ser.fromJson(json, FactoryResetCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}

	@Test
	public void testEquals() {
		FactoryResetCommand c1 = new FactoryResetCommand();
		FactoryResetCommand c2 = new FactoryResetCommand();
		
		assertEquals(c1, c2);
		
		c1.setTrxnId("aaa");
		assertFalse(c1.equals(c2));
		assertFalse(c2.equals(c1));
		
		c2.setTrxnId("aaa");
		assertTrue(c1.equals(c2));
		assertTrue(c2.equals(c1));
				
	}
	
}
