package com.iris.ipcd.msg;

import org.junit.After;
import org.junit.Before;

import com.iris.ipcd.msg.Device;
import com.iris.ipcd.msg.Status;
import com.iris.ipcd.msg.Status.Result;
import com.iris.ipcd.ser.IpcdSerializer;

public class SerializerTest {
	
	protected IpcdSerializer ser;
	
	protected Device device;
	protected Status status;

	@Before
	public void setUp() throws Exception {
		ser = new IpcdSerializer();
		
		Device device = new Device();
		device.setVendor("BlackBox");
		device.setModel("Multisensor2");
		device.setSn("00049B3C7A05");
		device.setIpcdver("1.0");
		
		Status status = new Status();
		status.setResult(Result.success);
		
	}

	@After
	public void tearDown() throws Exception {
	}

}