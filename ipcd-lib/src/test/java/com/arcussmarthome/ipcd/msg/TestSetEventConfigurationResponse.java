package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestSetEventConfigurationResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		SetEventConfigurationResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-seteventconfiguration-1.json")), SetEventConfigurationResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.SetEventConfiguration, response1.getRequest().getCommand());
		
		assertNotNull(response1.getRequest().getEnabledEvents());
		assertEquals(5, response1.getRequest().getEnabledEvents().size());
		assertTrue(response1.getRequest().getEnabledEvents().contains(Event.onBoot));
		assertTrue(response1.getRequest().getEnabledEvents().contains(Event.onDownloadComplete));
		assertTrue(response1.getRequest().getEnabledEvents().contains(Event.onDownloadFailed));
		assertTrue(response1.getRequest().getEnabledEvents().contains(Event.onUpdate));
		assertTrue(response1.getRequest().getEnabledEvents().contains(Event.onValueChange));
		
		assertNotNull(response1.getRequest().getEnabledValueChanges());
		assertEquals(2, response1.getRequest().getEnabledValueChanges().size());
		assertEquals(new Double(5.0), response1.getRequest().getEnabledValueChanges().get("ms2.temperature").getOnLessThan());
		assertEquals(new Double(23.0), response1.getRequest().getEnabledValueChanges().get("ms2.temperature").getOnGreaterThan());
		assertEquals(new Double(10.0), response1.getRequest().getEnabledValueChanges().get("ms2.batterylevel").getOnChangeBy());
		assertEquals(new Double(15.0), response1.getRequest().getEnabledValueChanges().get("ms2.batterylevel").getOnLessThan());
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		SetEventConfiguration r = response1.getResponse();
		assertNotNull(r);
		
		assertNotNull(r.getEnabledEvents());
		assertEquals(5, r.getEnabledEvents().size());
		assertTrue(r.getEnabledEvents().contains(Event.onBoot));
		assertTrue(r.getEnabledEvents().contains(Event.onDownloadComplete));
		assertTrue(r.getEnabledEvents().contains(Event.onDownloadFailed));
		assertTrue(r.getEnabledEvents().contains(Event.onUpdate));
		assertTrue(r.getEnabledEvents().contains(Event.onValueChange));
		
		assertNotNull(r.getEnabledValueChanges());
		assertEquals(2, r.getEnabledValueChanges().size());
		
		assertNotNull(r.getEnabledValueChanges().get("ms2.temperature"));
		assertFalse(r.getEnabledValueChanges().get("ms2.temperature").isOnChange());
		assertNull(r.getEnabledValueChanges().get("ms2.temperature").getOnChangeBy());
		assertNull(r.getEnabledValueChanges().get("ms2.temperature").getOnEquals());
		assertEquals(new Double(5.0), r.getEnabledValueChanges().get("ms2.temperature").getOnLessThan());
		assertEquals(new Double(23.0), r.getEnabledValueChanges().get("ms2.temperature").getOnGreaterThan());
		
		assertNotNull(r.getEnabledValueChanges().get("ms2.batterylevel"));
		assertFalse(r.getEnabledValueChanges().get("ms2.batterylevel").isOnChange());
		assertEquals(new Double(10.0), r.getEnabledValueChanges().get("ms2.batterylevel").getOnChangeBy());
		assertNull(r.getEnabledValueChanges().get("ms2.batterylevel").getOnEquals());
		assertEquals(new Double(15.0), r.getEnabledValueChanges().get("ms2.batterylevel").getOnLessThan());
		assertNull(r.getEnabledValueChanges().get("ms2.batterylevel").getOnGreaterThan());

	}
	
	@Test
	public void testSer() {
		
		SetEventConfigurationResponse constructed = new SetEventConfigurationResponse();
		
		SetEventConfigurationCommand command = new SetEventConfigurationCommand();
		
		command.setEnabledEvents(Arrays.asList(Event.onBoot, Event.onDownloadComplete, Event.onDownloadFailed, 
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
		command.setEnabledValueChanges(enabledValueChanges);
		
		SetEventConfiguration response = new SetEventConfiguration();
		
		response.setEnabledEvents(Arrays.asList(Event.onBoot, Event.onUpdate, Event.onFactoryReset, Event.onValueChange));
		response.setEnabledValueChanges(enabledValueChanges);
		
		constructed.setDevice(device);
		constructed.setRequest(command);
		constructed.setStatus(status);
		constructed.setResponse(response);
		
		String json = ser.toJson(constructed);
		SetEventConfigurationResponse deserialized = ser.fromJson(json, SetEventConfigurationResponse.class);
		
		assertEquals(constructed, deserialized);
	}

}
