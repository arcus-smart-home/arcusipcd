package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestGetEventConfigurationResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetEventConfigurationResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-geteventconfiguration-1.json")), GetEventConfigurationResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.GetEventConfiguration, response1.getRequest().getCommand());
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		GetEventConfiguration r = response1.getResponse();
		assertNotNull(r);

		assertNotNull(r.getSupportedEvents());
		assertEquals(6, r.getSupportedEvents().size());
		assertTrue(r.getSupportedEvents().contains(Event.onBoot));
		assertTrue(r.getSupportedEvents().contains(Event.onDownloadComplete));
		assertTrue(r.getSupportedEvents().contains(Event.onDownloadFailed));
		assertTrue(r.getSupportedEvents().contains(Event.onUpdate));
		assertTrue(r.getSupportedEvents().contains(Event.onFactoryReset));
		assertTrue(r.getSupportedEvents().contains(Event.onValueChange));
		
		assertNotNull(r.getEnabledEvents());
		assertEquals(4, r.getEnabledEvents().size());
		assertTrue(r.getEnabledEvents().contains(Event.onBoot));
		assertTrue(r.getEnabledEvents().contains(Event.onUpdate));
		assertTrue(r.getEnabledEvents().contains(Event.onFactoryReset));
		assertTrue(r.getEnabledEvents().contains(Event.onValueChange));
		
		assertNotNull(r.getSupportedValueChanges());
		assertEquals(6, r.getSupportedValueChanges().size());
		assertNotNull(r.getSupportedValueChanges().get("ms2.temperature"));
		assertTrue(r.getSupportedValueChanges().get("ms2.temperature").contains(ThresholdRule.onChange));
		assertTrue(r.getSupportedValueChanges().get("ms2.temperature").contains(ThresholdRule.onChangeBy));
		assertTrue(r.getSupportedValueChanges().get("ms2.temperature").contains(ThresholdRule.onEquals));
		assertTrue(r.getSupportedValueChanges().get("ms2.temperature").contains(ThresholdRule.onLessThan));
		assertTrue(r.getSupportedValueChanges().get("ms2.temperature").contains(ThresholdRule.onGreaterThan));
		assertNotNull(r.getSupportedValueChanges().get("ms2.humidity"));
		assertTrue(r.getSupportedValueChanges().get("ms2.humidity").contains(ThresholdRule.onChange));
		assertTrue(r.getSupportedValueChanges().get("ms2.humidity").contains(ThresholdRule.onChangeBy));
		assertTrue(r.getSupportedValueChanges().get("ms2.humidity").contains(ThresholdRule.onEquals));
		assertTrue(r.getSupportedValueChanges().get("ms2.humidity").contains(ThresholdRule.onLessThan));
		assertTrue(r.getSupportedValueChanges().get("ms2.humidity").contains(ThresholdRule.onGreaterThan));
		assertNotNull(r.getSupportedValueChanges().get("ms2.luminosity"));
		assertTrue(r.getSupportedValueChanges().get("ms2.luminosity").contains(ThresholdRule.onChange));
		assertTrue(r.getSupportedValueChanges().get("ms2.luminosity").contains(ThresholdRule.onChangeBy));
		assertTrue(r.getSupportedValueChanges().get("ms2.luminosity").contains(ThresholdRule.onEquals));
		assertTrue(r.getSupportedValueChanges().get("ms2.luminosity").contains(ThresholdRule.onLessThan));
		assertTrue(r.getSupportedValueChanges().get("ms2.luminosity").contains(ThresholdRule.onGreaterThan));
		assertNotNull(r.getSupportedValueChanges().get("ms2.lightsensorenabled"));
		assertTrue(r.getSupportedValueChanges().get("ms2.lightsensorenabled").contains(ThresholdRule.onChange));
		assertTrue(r.getSupportedValueChanges().get("ms2.lightsensorenabled").contains(ThresholdRule.onEquals));
		assertNotNull(r.getSupportedValueChanges().get("ms2.temperaturescale"));
		assertTrue(r.getSupportedValueChanges().get("ms2.temperaturescale").contains(ThresholdRule.onChange));
		assertTrue(r.getSupportedValueChanges().get("ms2.temperaturescale").contains(ThresholdRule.onEquals));

		assertNotNull(r.getEnabledValueChanges());
		assertEquals(3, r.getEnabledValueChanges().size());
		assertNotNull(r.getEnabledValueChanges().get("ms2.temperature"));
		assertFalse(r.getEnabledValueChanges().get("ms2.temperature").isOnChange());
		assertNull(r.getEnabledValueChanges().get("ms2.temperature").getOnChangeBy());
		assertNull(r.getEnabledValueChanges().get("ms2.temperature").getOnEquals());
		assertEquals(new Double(2.0), r.getEnabledValueChanges().get("ms2.temperature").getOnLessThan());
		assertEquals(new Double(27.0), r.getEnabledValueChanges().get("ms2.temperature").getOnGreaterThan());
		
		assertNotNull(r.getEnabledValueChanges().get("ms2.batterylevel"));
		assertFalse(r.getEnabledValueChanges().get("ms2.batterylevel").isOnChange());
		assertEquals(new Double(10.0), r.getEnabledValueChanges().get("ms2.batterylevel").getOnChangeBy());
		assertNull(r.getEnabledValueChanges().get("ms2.batterylevel").getOnEquals());
		assertEquals(new Double(15.0), r.getEnabledValueChanges().get("ms2.batterylevel").getOnLessThan());
		assertNull(r.getEnabledValueChanges().get("ms2.batterylevel").getOnGreaterThan());

		assertNotNull(r.getEnabledValueChanges().get("ms2.lightsensorenabled"));
		assertTrue(r.getEnabledValueChanges().get("ms2.lightsensorenabled").isOnChange());
		assertNull(r.getEnabledValueChanges().get("ms2.lightsensorenabled").getOnChangeBy());
		assertNull(r.getEnabledValueChanges().get("ms2.lightsensorenabled").getOnEquals());
		assertNull(r.getEnabledValueChanges().get("ms2.lightsensorenabled").getOnLessThan());
		assertNull(r.getEnabledValueChanges().get("ms2.lightsensorenabled").getOnGreaterThan());

		
	}
	
	@Test
	public void testSer() {
		
		GetEventConfigurationResponse constructed = new GetEventConfigurationResponse();
		
		GetEventConfigurationCommand command = new GetEventConfigurationCommand();
		
		GetEventConfiguration response = new GetEventConfiguration();
		
		response.setSupportedEvents(Arrays.asList(Event.onBoot, Event.onDownloadComplete, Event.onDownloadFailed, Event.onUpdate, Event.onFactoryReset, Event.onValueChange));
		
		response.setEnabledEvents(Arrays.asList(Event.onBoot, Event.onUpdate, Event.onFactoryReset, Event.onValueChange));
		
		Map<String,List<ThresholdRule>> supportedValueChanges = new HashMap<String, List<ThresholdRule>>();
		supportedValueChanges.put("ms2.temperature", Arrays.asList(ThresholdRule.onChange, ThresholdRule.onChangeBy,
				ThresholdRule.onEquals, ThresholdRule.onLessThan, ThresholdRule.onGreaterThan));
		supportedValueChanges.put("ms2.humidity", Arrays.asList(ThresholdRule.onChange, ThresholdRule.onChangeBy,
				ThresholdRule.onEquals, ThresholdRule.onLessThan, ThresholdRule.onGreaterThan));
		supportedValueChanges.put("ms2.luminosity", Arrays.asList(ThresholdRule.onChange, ThresholdRule.onChangeBy,
				ThresholdRule.onEquals, ThresholdRule.onLessThan, ThresholdRule.onGreaterThan));
		supportedValueChanges.put("ms2.lightsensorenabled", Arrays.asList(ThresholdRule.onChange, ThresholdRule.onEquals));
		supportedValueChanges.put("ms2.temperaturescale", Arrays.asList(ThresholdRule.onChange, ThresholdRule.onEquals));
		response.setSupportedValueChanges(supportedValueChanges);;
		
		
		Map<String,ValueChangeThreshold> enabledValueChanges = new HashMap<String,ValueChangeThreshold>();
		ValueChangeThreshold t1 = new ValueChangeThreshold();
		t1.setOnLessThan(2.0);
		t1.setOnGreaterThan(27.0);
		enabledValueChanges.put("ms2.temperature", t1);
		ValueChangeThreshold t2 = new ValueChangeThreshold();
		t2.setOnLessThan(10.0);
		t2.setOnGreaterThan(15.0);
		enabledValueChanges.put("ms2.batterylevel", t2);
		ValueChangeThreshold t3 = new ValueChangeThreshold();
		t3.setOnChange(true);
		enabledValueChanges.put("ms2.lightsensorenabled", t3);
		response.setEnabledValueChanges(enabledValueChanges);
		
		constructed.setDevice(device);
		constructed.setRequest(command);
		constructed.setStatus(status);
		constructed.setResponse(response);
		
		String json = ser.toJson(constructed);
		GetEventConfigurationResponse deserialized = ser.fromJson(json, GetEventConfigurationResponse.class);
		
		assertEquals(constructed, deserialized);
	}


}
