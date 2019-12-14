package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

public class TestSetEventConfigurationCommand extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		SetEventConfigurationCommand command1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("command-seteventconfiguration-1.json")), SetEventConfigurationCommand.class);
	
		assertEquals(CommandType.SetEventConfiguration, command1.getCommand());
		
		assertNotNull(command1.getEnabledEvents());
		assertEquals(6, command1.getEnabledEvents().size());
		assertTrue(command1.getEnabledEvents().contains(Event.onBoot));
		assertTrue(command1.getEnabledEvents().contains(Event.onDownloadComplete));
		assertTrue(command1.getEnabledEvents().contains(Event.onDownloadFailed));
		assertTrue(command1.getEnabledEvents().contains(Event.onUpdate));
		assertTrue(command1.getEnabledEvents().contains(Event.onConnect));
		assertTrue(command1.getEnabledEvents().contains(Event.onValueChange));
		
		assertNotNull(command1.getEnabledValueChanges());
		assertEquals(2, command1.getEnabledValueChanges().size());
		assertEquals(new Double(5.0), command1.getEnabledValueChanges().get("ms2.temperature").getOnLessThan());
		assertEquals(new Double(23.0), command1.getEnabledValueChanges().get("ms2.temperature").getOnGreaterThan());
		assertEquals(new Double(10.0), command1.getEnabledValueChanges().get("ms2.batterylevel").getOnChangeBy());
		assertEquals(new Double(15.0), command1.getEnabledValueChanges().get("ms2.batterylevel").getOnLessThan());
	}
	
	@Test
	public void testSer() {
		
		SetEventConfigurationCommand constructed = new SetEventConfigurationCommand();
		constructed.setEnabledEvents(Arrays.asList(Event.onBoot, Event.onDownloadComplete, Event.onDownloadFailed, 
				Event.onUpdate, Event.onValueChange));
		Map<String,ValueChangeThreshold> enabledValueChanges = new HashMap<String,ValueChangeThreshold>();
		ValueChangeThreshold t1 = new ValueChangeThreshold();
		t1.setOnLessThan(2.0);
		t1.setOnGreaterThan(23.0);
		enabledValueChanges.put("ms2.temperature", t1);
		ValueChangeThreshold t2 = new ValueChangeThreshold();
		t2.setOnChangeBy(10.0);
		t2.setOnLessThan(15.0);
		enabledValueChanges.put("ms2.batterylevel", t2);
		constructed.setEnabledValueChanges(enabledValueChanges);
		
		
		String json = ser.toJson(constructed);
		SetEventConfigurationCommand deserialized = ser.fromJson(json, SetEventConfigurationCommand.class);
		
		assertEquals(constructed, deserialized);
		assertTrue(json.replaceAll("\\s", "").startsWith("{\"command\":"));
	}

	
	
}
