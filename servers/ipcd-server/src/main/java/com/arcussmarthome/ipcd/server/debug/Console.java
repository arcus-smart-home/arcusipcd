package com.arcussmarthome.ipcd.server.debug;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.arcussmarthome.ipcd.msg.AbstractCommand;
import com.arcussmarthome.ipcd.msg.DownloadCommand;
import com.arcussmarthome.ipcd.msg.Event;
import com.arcussmarthome.ipcd.msg.FactoryResetCommand;
import com.arcussmarthome.ipcd.msg.GetDeviceInfoCommand;
import com.arcussmarthome.ipcd.msg.GetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.GetParameterInfoCommand;
import com.arcussmarthome.ipcd.msg.GetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.GetReportConfigurationCommand;
import com.arcussmarthome.ipcd.msg.LeaveCommand;
import com.arcussmarthome.ipcd.msg.RebootCommand;
import com.arcussmarthome.ipcd.msg.SetDeviceInfoCommand;
import com.arcussmarthome.ipcd.msg.SetEventConfigurationCommand;
import com.arcussmarthome.ipcd.msg.SetParameterValuesCommand;
import com.arcussmarthome.ipcd.msg.SetReportConfigurationCommand;
import com.arcussmarthome.ipcd.msg.ThresholdRule;
import com.arcussmarthome.ipcd.msg.ValueChangeThreshold;
import com.arcussmarthome.ipcd.server.session.IpcdSession;
import com.arcussmarthome.ipcd.server.session.SessionRegistry;

public class Console {

	private static final Logger logger = LoggerFactory.getLogger("Console");
	
	private IpcdSession debugSession;
	
	public Console() {
		// TODO Auto-generated constructor stub
	}

	public void handleInput(String msg) {
		if (msg == null)
			return;
		
		String[] parts = msg.split(" ");
		if (parts[0].equals("")) {
			return;
		}
		
		if (parts[0].equalsIgnoreCase("device")) {
			handleDevice(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("GetParameterValues")) {
			handleGetParameterValues(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("SetParameterValues")) {
			handleSetParameterValues(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("GetParameterInfo")) {
			handleGetParameterInfo(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("GetDeviceInfo")) {
			handleGetDeviceInfo(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("SetDeviceInfo")) {
			handleSetDeviceInfo(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("GetReportConfiguration")) {
			handleGetReportConfiguration(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("SetReportConfiguration")) {
			handleSetReportConfiguration(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("GetEventConfiguration")) {
			handleGetEventConfiguration(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("SetEventConfiguration")) {
			handleSetEventConfiguration(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("Download")) {
			handleDownload(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("Upload")) {
			handleUpload(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("Reboot")) {
			handleReboot(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("FactoryReset")) {
			handleFactoryReset(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("Leave")) {
			handleLeave(parts);
			return;
		}
		if (parts[0].equalsIgnoreCase("help")) {
			printHelp(parts);
			return;
		}
		
		logger.error ("error: command " + parts[0] + " not recognized\nuse device to select a device session, then enter a command to send to the selected device");
	}
	
	
	private void printHelp(String[] parts) {
		if (parts.length == 1) {
			logger.info("\nUsage: help [command]\n" +
					    "Commands are [Device, GetParameterValues, SetParameterValues, GetParameterInfo, GetDeviceInfo,\n" +
						"GetReportConfiguration, SetReportConfiguration, GetEventConfiguration, SetEventConfiguration, \n" +
					    "Download, Upload, Reboot, FactoryReset, Leave, Help].  For more information on a command, enter \n" +
						"help [command].\n" +
					    "Examples:\n  help\n  help SetParameterValues");
			return;
		} 
		if (parts[1].equalsIgnoreCase("help")) {
			logger.info("\nUsage: help [command]\n" + 
		                "Help yourself by reading help; help help is unhelpful.");
			return;
		}
		if (parts[1].equalsIgnoreCase("Device")) {
			logger.info("\nUsage: device <vendor> <model> <serialnum>\n" +
						"Device is a console-only command (not part of the IPCD protocol) that selects a device to\n"+
					    "interact with on the console.  After specifying a device, all protocol commands entered \n" +
					    "into the console are sent to that device.  An error will be returned if no device with the\n" +
					    "provided identity is currently connected\n" +
					    "Examples:\n  Device BlackBox Multisensor2 00049B3C7A05");
			return;
		}
		if (parts[1].equalsIgnoreCase("GetParameterValues")) {
			logger.info("\nUsage: GetParameterValues [txnid=id] [parameter1] [parameter2] ... [parameterN]\n" +
						"Sends a GetParameterValues command to the currently selected device.  Parameters to\n" +
						"retrieve may be specified as additional arguments.  If no parameters are specified\n" +
						"then all parameters should be returned (see the IPCD specification).\n" +
					    "Examples:\n  GetParameterValues\n  GetParameterValues temperature relhumidity units");
			return;
		}
		if (parts[1].equalsIgnoreCase("SetParameterValues")) {
			logger.info("\nUsage: SetParameterValues [txnid=id] [parameter1=value1] [parameter2=value2] ... [parameterN=valueN]\n" +
						"Sends a SetParameterValues command to the currently selected device.  Parameter\n" +
						"name=value pairs may be specified as additional arguments.  If no parameters are specified\n" +
						"then all parameters should be returned (see the IPCD specification).  The console will attempt\n" +
						"to determine the type of the value but if a value is surrounded by quotes, then it will be\n" +
						"sent as a string.\n" +
					    "Examples:\n  SetParameterValues temperature=20.0\n  SetParameterValues temperature=20.0 units=C");
			return;
		}
		if (parts[1].equalsIgnoreCase("GetParameterInfo")) {
			logger.info("\nUsage: GetParameterInfo [txnid=id]\n" +
						"Sends a GetParameterInfo command to the currently selected device.\n" +
					    "Examples:\n  GetParameterInfo");
			return;
		}
		if (parts[1].equalsIgnoreCase("GetDeviceInfo")) {
			logger.info("\nUsage: GetDeviceInfo [txnid=id]\n" +
						"Sends a GetDeviceInfo command to the currently selected device.\n" +
					    "Examples:\n  GetDeviceInfo");
			return;
		}
		if (parts[1].equalsIgnoreCase("SetDeviceInfo")) {
			logger.info("\nUsage: SetDeviceInfo [txnid=id] [field1=value1] [field2=value2] ... [fieldN=valueN]\n" +
						"Sends a SetDeviceInfo command to the currently selected device.  Field\n" +
						"name=value pairs may be specified as additional arguments. The console will attempt\n" +
						"to determine the type of the value but if a value is surrounded by quotes, then it will be\n" +
						"sent as a string.\n" +
					    "Examples:\n  SetDeviceInfo connectUrl=\"https://ipthings.inetothings.net/ipcd\"");
			return;
		}
		if (parts[1].equalsIgnoreCase("GetReportConfiguration")) {
			logger.info("\nUsage: GetReportConfiguration [txnid=id]\n" +
						"Sends a GetReportConfiguration command to the currently selected device.\n" +
					    "Examples:\n  GetReportConfiguration");
			return;
		}
		if (parts[1].equalsIgnoreCase("SetReportConfiguration")) {
			logger.info("\nUsage: SetReportConfiguration [txnid=id] [report_interval] [parameter1] ... [parameterN]\n" +
						"Sends a GetReportConfiguration command to the currently selected device.  If the\n" +
						"first argument is a number, the console interprets that as a report interval\n" +
						"in seconds.  Otherwise, all other arguments are assumed to be parameters that\n" +
						"should be included in subsequent reports from the device (see the IPCD\n" +
						"specification)." +
					    "Examples:\n  SetReportConfiguration 300\n  SetReportConfiguration 60 temperature relhumidity\n  SetReportConfiguration relhumidity");
			return;
		}
		if (parts[1].equalsIgnoreCase("GetEventConfiguration")) {
			logger.info("\nUsage: GetEventConfiguration [txnid=id]\n" +
						"Sends a GetEventConfiguration command to the currently selected device.\n" +
					    "Examples:\n  GetEventConfiguration");
			return;
		}
		if (parts[1].equalsIgnoreCase("SetEventConfiguration")) {
			logger.info("\nUsage: SetEventConfiguration [txnid=id] event1,event2,eventN parameter1=valchange_event[:valchange_value]\n" +
						"Sends a SetEventConfiguration command to the currently selected device.  The\n" +
					    "first argument must be a comma-delimited set of events to enable which is a\n" +
						"subset of [onBoot, onConnect onDownloadComplete, onDownloadFailed, onUpdate,\n" +
					    "onFactoryReset, onValueChange].  Each subsequent argument represents the set of\n" +
						"value change thresholds for a given parameter where valid value change events\n" +
					    "may include [onChange, onChangeBy, onEquals, onLessThan, onGreaterThan].  Only\n" +
						"those events supported by the device and only those change change thresholds\n" +
					    "supported by the specific parameter will be accepted (see the IPCD specification)\n" +
					    "Examples:\n  SetEventConfiguration onBoot,onConnect,onUpdate,onValueChange temparature=onLessThan:5,onGreaterThan:23 relhumidity=onChange");
			return;
		}
		if (parts[1].equalsIgnoreCase("Download")) {
			logger.info("\nUsage: Download [txnid=id] url\n" +
						"Sends a Download command to the currently selected device which causes it to\n" +
						"fetch the file at the given URL." +
					    "Examples:\n  Download https://myservice.com/images/BlackBox/Multisensor2/2.03.bin");
			return;
		}
		if (parts[1].equalsIgnoreCase("Upload")) {
			logger.info("The Upload command is not supported in this version of the IPCD protocol.");
			return;
		}
		if (parts[1].equalsIgnoreCase("Reboot")) {
			logger.info("\nUsage: Reboot [txnid=id]\n" +
						"Sends a Reboot command to the currently selected device.\n" +
					    "Examples:\n  Reboot");
			return;
		}
		if (parts[1].equalsIgnoreCase("FactoryReset")) {
			logger.info("\nUsage: FactoryReset [txnid=id]\n" +
						"Sends a FactoryReset command to the currently selected device.\n" +
					    "Examples:\n  FactoryReset");
			return;
		}
		if (parts[1].equalsIgnoreCase("Leave")) {
			logger.info("\nUsage: Leave [txnid=id]\n" +
						"Sends a Leave command to the currently selected device.\n" +
					    "Examples:\n  Leave");
			return;
		}
		
	}
	
	private void handleDevice(String[] parts) {
		if (parts.length != 4) {
			logger.error("error: usage for device command is\ndevice vendor model sn");
			return;
		}
		String id = SessionRegistry.genDeviceKey(parts[1], parts[2], parts[3]);
		IpcdSession session = SessionRegistry.getByKey(id);
		if (session == null) {
			logger.error("error: device session identified by " + id + " not found");
		} else {
			debugSession = session;
		}
		if (debugSession != null) {
			logger.info("device session [" + SessionRegistry.genDeviceKey(debugSession.getDevice()) + "]");
		}
	}
	
	private boolean processTxnid(String[] parts, AbstractCommand cmd) {
		if (parts.length < 2) { return false; }
		if (parts[1] != null && parts[1].startsWith("txnid=")) {
			cmd.setTrxnId((parts[1].substring(6)));
			return true;
		}
		return false;
	}
	
	private void handleGetParameterInfo(String[] parts) {
		// command requires a valid device session
		if (!checkDevice()) { return; }

		// construct message
		GetParameterInfoCommand cmd = new GetParameterInfoCommand();
		processTxnid(parts, cmd);
		debugSession.sendCommand(cmd);
	}
	
	private void handleGetDeviceInfo(String[] parts) {
		// command requires a valid device session
		if (!checkDevice()) { return; }

		// construct message
		GetDeviceInfoCommand cmd = new GetDeviceInfoCommand();
		processTxnid(parts, cmd);
		debugSession.sendCommand(cmd);
	}
	
	private void handleSetDeviceInfo(String[] parts) {
		// command requires a valid device session
		if (!checkDevice()) { return; }
		SetDeviceInfoCommand cmd = new SetDeviceInfoCommand();
		int startidx = 1;
		if (processTxnid(parts, cmd)) {
			startidx++;
		}
		
		Map<String,Object> values = new LinkedHashMap<String,Object>();
		for (int i = startidx; i < parts.length; i++) {
			String value = parts[i];
			String[] pair = value.split("=");
			if (pair.length == 2) { 
				values.put(pair[0], castValue(pair[1]));
			} else {
				logger.error("error: usage for SetDeviceInfo command is\nSetDeviceInfo [txnid=id] param1=value1 [param2=value2] ...");
			}
		}
		cmd.setValues(values);
		debugSession.sendCommand(cmd);
	}
	
	private void handleGetReportConfiguration(String[] parts) {
		if (!checkDevice()) { return; }
		
		// construct message
		GetReportConfigurationCommand cmd = new GetReportConfigurationCommand();
		processTxnid(parts, cmd);
		debugSession.sendCommand(cmd);
	}
	
	private void handleSetReportConfiguration(String[] parts) {
		if (!checkDevice()) { return; }
		
		// construct message
		SetReportConfigurationCommand cmd = new SetReportConfigurationCommand();
		
		int startidx = 1;
		if (processTxnid(parts, cmd)) {
			startidx++;
		}
		
		if (parts.length == (startidx)) {
			logger.error("error: usage for SetReportConfiguration command is\nSetReportConfiguration [txnid=id] [report_interval] [parameter1] ... [parameterN]");
			return;
		}
		
		// check to see if the first part looks like an interval (number)
		Integer interval = null;
		try {
			interval = Integer.valueOf(parts[startidx]);
			startidx++;
		} catch (NumberFormatException e) {
			// swallow
		}
		if (interval != null) {
			cmd.setInterval(interval);
		}
		cmd.setParameters(Arrays.asList(Arrays.copyOfRange(parts, startidx, parts.length)));
		debugSession.sendCommand(cmd);
	}
		
	
	private void handleGetParameterValues(String[] parts) {
		
		// command requires a valid device session
		if (!checkDevice()) { return; }

		// construct message
		GetParameterValuesCommand cmd = new GetParameterValuesCommand();
		int startidx = 1;
		if (processTxnid(parts, cmd)) {
			startidx++;
		}
		cmd.setParameters(Arrays.asList(Arrays.copyOfRange(parts, startidx, parts.length)));
		debugSession.sendCommand(cmd);
	}
	
	private void handleSetParameterValues(String[] parts) {
		// command requires a valid device session
		if (!checkDevice()) { return; }
		SetParameterValuesCommand cmd = new SetParameterValuesCommand();
		int startidx = 1;
		if (processTxnid(parts, cmd)) {
			startidx++;
		}
		
		Map<String,Object> values = new LinkedHashMap<String,Object>();
		for (int i = startidx; i < parts.length; i++) {
			String value = parts[i];
			String[] pair = value.split("=");
			if (pair.length == 2) { 
				values.put(pair[0], castValue(pair[1]));
			} else {
				logger.error("error: usage for SetParameterValues command is\nSetParameterValues [txnid=id] param1=value1 [param2=value2] ...");
			}
		}
		cmd.setValues(values);
		debugSession.sendCommand(cmd);
	}
	
	private Object castValue(String v) {
		
		if (isQuoted(v)) {
			return v.substring(1, v.length() - 1);
		}
		
		Object value;
		try {
			value = Integer.valueOf(v);
			return value;
		} catch (Exception e) {}
		
		try {
			value = Double.valueOf(v);
			return value;
		} catch (Exception e) {}
		
		if ("true".equalsIgnoreCase(v) || "false".equalsIgnoreCase(v)) {
			try {
				value = Boolean.valueOf(v);
				return value;
			} catch (Exception e) {}
		}
		return v;
	}
	
	private boolean isQuoted(String s) {
		return (s != null && s.length() >= 2 && s.startsWith("\"") && s.endsWith("\""));
	}
	
	private void handleGetEventConfiguration(String[] parts) {
		if (!checkDevice()) { return; }
		GetEventConfigurationCommand cmd = new GetEventConfigurationCommand();
		processTxnid(parts, cmd);
		debugSession.sendCommand(cmd);
	}

	private void handleSetEventConfiguration(String[] parts) {
		if (!checkDevice()) { return; }

		SetEventConfigurationCommand cmd = new SetEventConfigurationCommand();
		int startidx = 1;
		if (processTxnid(parts, cmd)) {
			startidx++;
		}
		
		if (parts.length == (startidx)) {
			logger.error("error: usage for SetEventConfiguration command is\nSetEventConfiguration [txnid=id] event1,event2,eventN parameter1=valchange_event[:valchange_value]");
			return;
		}
		
		String[] eventStrs = parts[startidx].split(",");
		List<Event> events = new ArrayList<Event>();
		for (String event: eventStrs) {
			try {
				Event e = Event.valueOf(event);
				events.add(e);
			} catch (Exception e) {
				logger.error("error: " + event + " is not a valid event.  Must be in [onBoot, onConnect onDownloadComplete, onDownloadFailed, onUpdate, onFactoryReset, onValueChange]");
				return;
			}
		}
		cmd.setEnabledEvents(events);
		startidx++;
		
		Map<String,ValueChangeThreshold> valueChanges = new LinkedHashMap<String,ValueChangeThreshold>();
		// for each parameter ...
		// temparature=onLessThan:5,onGreaterThan:23
		for (int i = startidx; i < parts.length; i++) {
			int eqpos = parts[i].indexOf('=');
			String var = parts[i].substring(0, eqpos);
			String thresholds = parts[i].substring(eqpos+1);
			String[] exps = thresholds.split(",");
			ValueChangeThreshold threshold = new ValueChangeThreshold();
			
			for (String exp: exps) {
				int colpos = exp.indexOf(':');
				if (colpos == 0) { colpos = exp.length(); } 
				String expop = exp.substring(0, colpos);
				String value = null;
				if (colpos < exp.length()) {
					value = exp.substring(colpos+1);
				}
				ThresholdRule rule = null;
				try {
					rule = ThresholdRule.valueOf(expop); 
				} catch (Exception e) {
					logger.error("error: " + expop + " is not a valid value change threshold operator.  Must be in [onChange, onChangeBy, onEquals, onLessThan, onGreaterThan]");
					return;
				}
				switch (rule) {
					case onChange:
						try {
							threshold.setOnChange(Boolean.valueOf(value));
						} catch (Exception e) {
							logger.error("error: onChange threshold value for parameter " + var + " must be true or false");
							return;
						}
						break; 
					case onChangeBy:
						try {
							threshold.setOnChangeBy(Double.valueOf(value));
						} catch (NumberFormatException e) {
							logger.error("error: onChangeBy threshold value for parameter " + var + " must be a valid number");
							return;
						}
						break;
					case onEquals: 
						try {
							List<Object> equalValues = new ArrayList<Object>();
							equalValues.add(Double.valueOf(value));
							threshold.setOnEquals(equalValues);
						} catch (NumberFormatException e) {
							logger.error("error: onEquals threshold value for parameter " + var + " must be a valid number");
							return;
						}
						break;
					case onLessThan:
						try {
							threshold.setOnLessThan(Double.valueOf(value));
						} catch (NumberFormatException e) {
							logger.error("error: onChangeBy threshold value for parameter " + var + " must be a valid number");
							return;
						}
						break;
					case onGreaterThan: 
						try {
							threshold.setOnGreaterThan(Double.valueOf(value));
						} catch (NumberFormatException e) {
							logger.error("error: onGreaterThan threshold value for parameter " + var + " must be a valid number");
							return;
						}
						break;
				}
			}
			valueChanges.put(var, threshold);
		}
		cmd.setEnabledValueChanges(valueChanges);
		debugSession.sendCommand(cmd);
	}
	
	private void handleDownload(String parts[]) {
		if (!checkDevice()) { return; }
		if (parts.length < 2) {
			logger.error("error: usage for Downlaod command is\nDownload url [username] [password]");
			return;
		}
		
		DownloadCommand cmd = new DownloadCommand();
		int startidx = 1;
		if (processTxnid(parts, cmd)) {
			startidx++;
		}
		cmd.setUrl(parts[startidx]);
		if (parts.length > (startidx+1)) {
			cmd.setUsername(parts[startidx+1]);
		}
		if (parts.length > (startidx+2)) {
			cmd.setPassword(parts[startidx+2]);
		}
		debugSession.sendCommand(cmd);
	}
	
	private void handleUpload(String[] parts) {
		if (!checkDevice()) { return; }
		logger.warn("Upload is not currently supported in the debug console");
	}
	
	private void handleFactoryReset(String[] parts) {
		if (!checkDevice()) { return; }
		FactoryResetCommand cmd = new FactoryResetCommand();
		processTxnid(parts, cmd);
		debugSession.sendCommand(cmd);
	}
	
	private void handleLeave(String[] parts) {
		if (!checkDevice()) { return; }
		LeaveCommand cmd = new LeaveCommand();
		processTxnid(parts, cmd);
		debugSession.sendCommand(cmd);
	}
	
	private void handleReboot(String[] parts) {
		if (!checkDevice()) { return; }
		RebootCommand cmd = new RebootCommand();
		processTxnid(parts, cmd);
		debugSession.sendCommand(cmd);
	}

	private boolean checkDevice() {
		if (debugSession == null) {
			logger.error("error: no debug device selected, use the device command to select an existing session");
			return false;
		}
		return true;
	}
	
}

