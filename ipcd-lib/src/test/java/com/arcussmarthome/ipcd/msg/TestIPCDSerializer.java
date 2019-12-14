package com.arcussmarthome.ipcd.msg;

import static org.junit.Assert.*;

import java.io.InputStreamReader;

import org.junit.Test;

import com.arcussmarthome.ipcd.ser.InvalidMessageException;
import com.arcussmarthome.ipcd.ser.IpcdSerializer;

public class TestIPCDSerializer {

	
	@Test
	public void testParseClientMessage() throws InvalidMessageException {
		
		IpcdSerializer ser = new IpcdSerializer();
		ClientMessage m = null;
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("report-1.json")));
		assertTrue(m.getClass().equals(ReportAction.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("event-1.json")));
		assertTrue(m.getClass().equals(EventAction.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-download-1.json")));
		assertTrue(m.getClass().equals(DownloadResponse.class));		
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-factoryreset-1.json")));
		assertTrue(m.getClass().equals(FactoryResetResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-getdeviceinfo-1.json")));
		assertTrue(m.getClass().equals(GetDeviceInfoResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-geteventconfiguration-1.json")));
		assertTrue(m.getClass().equals(GetEventConfigurationResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-getparameterinfo-1.json")));
		assertTrue(m.getClass().equals(GetParameterInfoResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-getparametervalues-1.json")));
		assertTrue(m.getClass().equals(GetParameterValuesResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-getreportconfiguration-1.json")));
		assertTrue(m.getClass().equals(GetReportConfigurationResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-geteventconfiguration-1.json")));
		assertTrue(m.getClass().equals(GetEventConfigurationResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-setparametervalues-1.json")));
		assertTrue(m.getClass().equals(SetParameterValuesResponse.class));
		
		m = ser.parseClientMessage(new InputStreamReader(this.getClass().getResourceAsStream("response-setreportconfiguration-1.json")));
		assertTrue(m.getClass().equals(SetReportConfigurationResponse.class));
		
	}
	
//	@Test
//	public void testParseServerMessage() {
//		
//	}

}
