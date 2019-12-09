package com.arcussmarthome.ipcd.client.model;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

import com.google.gson.Gson;
import com.arcussmarthome.ipcd.msg.ConnectionType;
import com.arcussmarthome.ipcd.msg.ParameterAttrib;
import com.arcussmarthome.ipcd.msg.ThresholdRule;
import com.arcussmarthome.ipcd.msg.ValueChangeThreshold;

public class TestDeviceModel {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testSerialization() throws Exception {
		DeviceModel deviceModel = null;

		deviceModel = new DeviceModel();
		deviceModel.setVendor("BlackBox");
		deviceModel.setModel("Multisensor2");
		deviceModel.setSn("00049B3C7A05");
		deviceModel.setIpcdver("0.3");
		
		deviceModel.setConnectionType(ConnectionType.persistent);
		deviceModel.setConnectUrl("ws://localhost:8080/websockets");
		deviceModel.setBuffersize(2048);
		
		ParameterDefinition temp = new ParameterDefinition();
		temp.setName("ms2.temperature");
		temp.setAttrib(ParameterAttrib.readwrite);
		temp.setCeiling(100d);
		temp.setFloor(0d);
		temp.setCurrentValue(32d);
		
		temp.setSupportedValueChanges(Arrays.asList(ThresholdRule.onChange, ThresholdRule.onChangeBy, ThresholdRule.onEquals, ThresholdRule.onGreaterThan, ThresholdRule.onLessThan));
		
		ValueChangeThreshold tempVct = new ValueChangeThreshold();
		tempVct.setOnChange(true);
		temp.setEnabledValueChanges(tempVct);
		temp.setType("number");
		
		deviceModel.addParameter(temp);
		
		Gson gson = new Gson();
		String serialized = gson.toJson(deviceModel);
		
		System.out.println(serialized);
	}

}
