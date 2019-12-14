package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;
import java.util.Arrays;

import org.junit.Test;

import com.arcussmarthome.ipcd.msg.Status.Result;

public class TestGetDeviceInfoResponse extends SerializerTest {

	@Test
	public void testDeser() throws Exception {
		
		GetDeviceInfoResponse response1 = ser.fromJson(new InputStreamReader(this.getClass().getResourceAsStream("response-getdeviceinfo-1.json")), GetDeviceInfoResponse.class);
	
		assertNotNull(response1.getDevice());
		assertEquals("BlackBox", response1.getDevice().getVendor());
		assertEquals("Multisensor2", response1.getDevice().getModel());
		assertEquals("00049B3C7A05", response1.getDevice().getSn());
		assertEquals("1.0", response1.getDevice().getIpcdver());
		
		assertNotNull(response1.getRequest());
		assertEquals(CommandType.GetDeviceInfo, response1.getRequest().getCommand());
		
		assertNotNull(response1.getStatus());
		assertEquals(Result.success, response1.getStatus().getResult());
		assertTrue(response1.getStatus().getMessages().isEmpty());
		
		DeviceInfo r = response1.getResponse();
		assertNotNull(response1.getResponse());
		assertEquals("1.19-b118", r.getFwver());
		assertEquals(ConnectionType.on_demand, r.getConnection());
		assertEquals("https://ipthings.inetothings.net/ipcd", r.getConnectUrl());
		assertNotNull(r.getActions());
		assertEquals(2, r.getActions().size());
		assertTrue(r.getActions().contains(ActionType.Event));
		assertTrue(r.getActions().contains(ActionType.Report));
		assertNotNull(r.getCommands());
		assertEquals(8, r.getCommands().size());
		assertTrue(r.getCommands().contains(CommandType.GetDeviceInfo));
		assertTrue(r.getCommands().contains(CommandType.GetParameterValues));
		assertTrue(r.getCommands().contains(CommandType.SetParameterValues));
		assertTrue(r.getCommands().contains(CommandType.GetParameterInfo));
		assertTrue(r.getCommands().contains(CommandType.GetReportConfiguration));
		assertTrue(r.getCommands().contains(CommandType.SetReportConfiguration));
		assertTrue(r.getCommands().contains(CommandType.GetEventConfiguration));
		assertTrue(r.getCommands().contains(CommandType.SetEventConfiguration));
		
	}
	
	@Test
	public void testSer() {
		
		GetDeviceInfoResponse constructed = new GetDeviceInfoResponse();
		
		DeviceInfo response = new DeviceInfo();
		response.setFwver("1.19-b118");
		response.setConnection(ConnectionType.on_demand);
		response.setConnectUrl("https://ipthings.inetothings.net/ipcd");
		response.setUptime(981452);
		response.setActions(Arrays.asList(ActionType.Report, ActionType.Event));
		response.setCommands(Arrays.asList(CommandType.GetDeviceInfo, CommandType.GetParameterValues, 
				CommandType.SetParameterValues, CommandType.GetParameterInfo, 
				CommandType.GetReportConfiguration, CommandType.SetReportConfiguration, 
				CommandType.GetEventConfiguration, CommandType.SetEventConfiguration));		
		
		constructed.setDevice(device);
		constructed.setRequest(new GetDeviceInfoCommand());
		constructed.setStatus(status);
		constructed.setResponse(response);
		
		String json = ser.toJson(constructed);
		GetDeviceInfoResponse deserialized = ser.fromJson(json, GetDeviceInfoResponse.class);
		
		assertEquals(constructed, deserialized);
	}

}
