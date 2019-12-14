package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class TestEventAction extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		EventAction event1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("event-1.json")), EventAction.class);
	
		assertNotNull(event1.getDevice());
		assertEquals("BlackBox", event1.getDevice().getVendor());
		assertEquals("Multisensor2", event1.getDevice().getModel());
		assertEquals("00049B3C7A05", event1.getDevice().getSn());
		assertEquals("1.0", event1.getDevice().getIpcdver());
		
		assertNotNull(event1.getEvents());
		assertEquals(1, event1.getEvents().size());
		assertEquals(Event.onValueChange, event1.getEvents().get(0));
		
		assertNotNull(event1.getValueChanges());
		assertEquals(1, event1.getValueChanges().size());
		ValueChange vc1 = event1.getValueChanges().get(0);
		assertEquals("ms2.temperature", vc1.getParameter());
		assertEquals(22.0, vc1.getValue());
		assertEquals(ThresholdRule.onGreaterThan, vc1.getThresholdRule());
		assertEquals(20.0, vc1.getThresholdValue());
		
	}
	
	@Test
	public void testSer() {
		
		Device device = new Device();
		device.setVendor("BlackBox");
		device.setModel("Multisensor2");
		device.setSn("00049B3C7A05");
		device.setIpcdver("1.0");
		
		List<Event> events = new ArrayList<Event>();
		events.add(Event.onValueChange);
		
		List<ValueChange> valuechanges = new ArrayList<ValueChange>();
		ValueChange vc1 = new ValueChange();
		vc1.setParameter("");
		vc1.setValue(22.0);
		vc1.setThresholdrule(ThresholdRule.onGreaterThan);
		vc1.setThresholdValue(20.0);
		valuechanges.add(vc1);
		
		EventAction constructed = new EventAction();
		constructed.setDevice(device);
		constructed.setEvents(events);
		constructed.setValueChanges(valuechanges);
		
		String json = ser.toJson(constructed);
		EventAction deserialized = ser.fromJson(json, EventAction.class);
		
		assertEquals(constructed, deserialized);
	}

}
