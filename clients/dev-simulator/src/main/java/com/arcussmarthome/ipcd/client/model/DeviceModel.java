package com.arcussmarthome.ipcd.client.model;

import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ScheduledFuture;

import com.arcussmarthome.ipcd.msg.ActionType;
import com.arcussmarthome.ipcd.msg.CommandType;
import com.arcussmarthome.ipcd.msg.ConnectionType;
import com.arcussmarthome.ipcd.msg.Device;
import com.arcussmarthome.ipcd.msg.Event;
import com.arcussmarthome.ipcd.msg.GetEventConfiguration;
import com.arcussmarthome.ipcd.msg.ParameterInfo;
import com.arcussmarthome.ipcd.msg.SetEventConfiguration;
import com.arcussmarthome.ipcd.msg.ThresholdRule;
import com.arcussmarthome.ipcd.msg.ValueChangeThreshold;

public class DeviceModel {

	// device info
	// ID
	private String vendor;
	private String model;
	private String sn;
	
	// Configuration
	private String ipcdver;
	private String fwver;
	private ConnectionType connectionType;
	private String connectUrl;
	private int buffersize = 4096;
	
	private List<ActionType> supportedActions = Arrays.asList(ActionType.Report, ActionType.Event);
	private List<CommandType> supportedCommands = Arrays.asList(CommandType.GetDeviceInfo, CommandType.SetDeviceInfo, CommandType.GetParameterValues,
			CommandType.SetParameterValues, CommandType.GetParameterInfo, CommandType.GetReportConfiguration,
			CommandType.SetReportConfiguration, CommandType.GetEventConfiguration, CommandType.SetEventConfiguration);
	
	// event configuration
	private List<Event> supportedEvents = Arrays.asList(Event.onBoot, Event.onDownloadComplete, Event.onDownloadFailed,
			Event.onUpdate, Event.onFactoryReset, Event.onValueChange);
	private List<Event> enabledEvents = Arrays.asList(Event.onBoot, Event.onDownloadComplete, Event.onDownloadFailed,
			Event.onUpdate, Event.onFactoryReset, Event.onValueChange);

	//report configuration
	private List<String> reportParameterNames = new ArrayList<String>();
	
	// simulator properties
	private Integer simulatedRebootInterval = 20;
	private Integer simulatedUpgradeInterval = 2;
	
	// data model
	private Map<String, ParameterDefinition> parameters = new LinkedHashMap<String, ParameterDefinition>();
	
	// non serializable data
	private transient Channel channel;
	private transient ScheduledFuture reportFuture;
	private transient ScheduledFuture rebootFuture;
	private transient ScheduledFuture upgradeFuture;
	
	
	public DeviceModel() {
	}
	
	public String getId() {
		String id = vendor + "-" + model + "-" + sn;
		id.replace(' ', '_');
		id.replace('/', '_');
		return id;
	}
	
	public Device getDevice() {
		Device d = new Device();
		d.setVendor(getVendor());
		d.setModel(getModel());
		d.setSn(getSn());
		d.setIpcdver(getIpcdver());
		return d;
	}
	
	public void cancelFutures() {
		if (reportFuture != null && !reportFuture.isCancelled()) {
			reportFuture.cancel(true);
		}
		if (upgradeFuture != null && !upgradeFuture.isCancelled()) {
			upgradeFuture.cancel(true);
		}
		if (rebootFuture != null && !rebootFuture.isCancelled()) {
			rebootFuture.cancel(true);
		}
	}
	
	public Channel channel() {
		return channel;
	}
	
	public GetEventConfiguration getGetEventConfiguration() {
		GetEventConfiguration eventConfig = new GetEventConfiguration();
		eventConfig.setEnabledEvents(Collections.unmodifiableList(enabledEvents));
		eventConfig.setSupportedEvents(Collections.unmodifiableList(supportedEvents));
		Map<String,List<ThresholdRule>> supportedValueChanges = new LinkedHashMap<String,List<ThresholdRule>>();
		for (String parameter: parameters.keySet()) {
			supportedValueChanges.put(parameter, parameters.get(parameter).getSupportedValueChanges());
		}
		eventConfig.setSupportedValueChanges(supportedValueChanges);
		Map<String, ValueChangeThreshold> enabledValueChanges = new LinkedHashMap<String, ValueChangeThreshold>();
		for (String parameter: parameters.keySet()) {
			enabledValueChanges.put(parameter, parameters.get(parameter).getEnabledValueChanges());
		}
		eventConfig.setEnabledValueChanges(enabledValueChanges);
		return eventConfig;
	}
	
	public SetEventConfiguration getSetEventConfiguration() {
		SetEventConfiguration eventConfig = new SetEventConfiguration();
		eventConfig.setEnabledEvents(Collections.unmodifiableList(enabledEvents));
		Map<String, ValueChangeThreshold> enabledValueChanges = new LinkedHashMap<String, ValueChangeThreshold>();
		for (String parameter: parameters.keySet()) {
			enabledValueChanges.put(parameter, parameters.get(parameter).getEnabledValueChanges());
		}
		eventConfig.setEnabledValueChanges(enabledValueChanges);
		return eventConfig;
	}
	
	public void setReportFuture(ScheduledFuture reportFuture) {
		if (this.reportFuture != null) {
			this.reportFuture.cancel(true);
		}
		this.reportFuture = reportFuture;
	}
	
	public void setRebootFuture(ScheduledFuture rebootFuture) {
		this.rebootFuture = rebootFuture;
	}
	
	public boolean isSupportedEvent(Event event) {
		return supportedEvents.contains(event);
	}
	
	public boolean isRebooting() {
		return (rebootFuture != null && !rebootFuture.isDone());
	}
	
	public void setUpgradeFuture(ScheduledFuture upgradeFuture) {
		this.upgradeFuture = upgradeFuture;
	}
	
	public boolean isUpgrading() {
		return (upgradeFuture != null && !upgradeFuture.isDone());
	}
	
//----------------------------
	
	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getSn() {
		return sn;
	}

	public void setSn(String sn) {
		this.sn = sn;
	}

	public String getIpcdver() {
		return ipcdver;
	}

	public void setIpcdver(String ipcdver) {
		this.ipcdver = ipcdver;
	}

	public String getFwver() {
		return fwver;
	}

	public void setFwver(String fwver) {
		this.fwver = fwver;
	}

	public ConnectionType getConnectionType() {
		return connectionType;
	}

	public void setConnectionType(ConnectionType connectionType) {
		this.connectionType = connectionType;
	}

	public String getConnectUrl() {
		return connectUrl;
	}

	public void setConnectUrl(String connectUrl) {
		this.connectUrl = connectUrl;
	}
	
	public int getBuffersize() {
		return this.buffersize;
	}
	
	public void setBuffersize(int buffersize) {
		this.buffersize = buffersize;
	}

	public List<ActionType> getSupportedActions() {
		return supportedActions;
	}

	public void setSupportedActions(List<ActionType> supportedActions) {
		this.supportedActions = supportedActions;
	}

	public List<CommandType> getSupportedCommands() {
		return supportedCommands;
	}

	public void setSupportedCommands(List<CommandType> supportedCommands) {
		this.supportedCommands = supportedCommands;
	}

	public void setSupportedEvents(List<Event> supportedEvents) {
		this.supportedEvents = supportedEvents;
	}
	
	public List<Event> getSupportedEvents() {
		return supportedEvents;
	}
	
	public void setEnabledEvents(List<Event> enabledEvents) throws ValidationException {
		List<Event> checked = new ArrayList<Event>();
		for (Event e: enabledEvents) {
			if (!supportedEvents.contains(e)) throw new ValidationException("Cannot enable event " + e + " because it is not supported");
			checked.add(e);
		}
		this.enabledEvents = checked;
	}

	public List<Event> getEnabledEvents() {
		return enabledEvents;
	}
	
	public Map<String,ParameterInfo> getParameterInfos() {
		Map<String,ParameterInfo> paramInfos = new LinkedHashMap<String,ParameterInfo>();
		for (String name: parameters.keySet()) {
			paramInfos.put(name, parameters.get(name).getParameterInfo());
		}
		return paramInfos;
	}

	public void setReportParameterNames(List<String> reportParameterNames) throws ValidationException {
		List<String> checked = new ArrayList<String>();
		
		for (String name: reportParameterNames) {
			if (parameters.get(name) == null) {
				throw new ValidationException("Parameter " + name + " is not defined in the device data model");
			}
			checked.add(name);
		}
		this.reportParameterNames = checked;
	}
	
	public List<String> getReportParameterNames() {
		return reportParameterNames;
	}
	
	public List<String> getParameterNames() {
		Set<String> parameterNames = parameters.keySet();
		return Arrays.asList(parameterNames.toArray(new String[parameterNames.size()]));
	}
	
	public Object getParameterValue(boolean returnWriteOnlyAsNull, String name) {
	   ParameterDefinition pd = parameters.get(name);
	   if (pd==null) {
	      throw new RuntimeException("Unknown Parameter: " + name );
	   }
	   return pd.getCurrentValue(returnWriteOnlyAsNull);
	}
	
	public ParameterDefinition getParameter(String name) {
		return parameters.get(name);
	}
	
	public void setParameterValue(String name, Object value) throws ValidationException {
		if (!parameters.containsKey(name)) throw new ValidationException("Parameter " + name + " is not defined");
		
		ParameterDefinition param = parameters.get(name);
		param.setCurrentValue(value);
	}
	
	public void clearParameterEnabledValueChanges() {
		for (String name: parameters.keySet()) {
			parameters.get(name).clearEnabledValueChanges();
		}
	}
	
	public void setParameterEnabledValueChanges(String name, ValueChangeThreshold enabledValueChanges) throws ValidationException {
		if (!parameters.containsKey(name)) throw new ValidationException("Parameter " + name + " is not defined");
		parameters.get(name).setEnabledValueChanges(enabledValueChanges);
	}
	
	public void addParameter(ParameterDefinition parameter) {
		this.parameters.put(parameter.getName(), parameter);
	}
	
	public void removeParameter(String name) {
		this.parameters.remove(name);
	}

	public Integer getSimulatedRebootInterval() {
		return simulatedRebootInterval;
	}

	public void setSimulatedRebootInterval(Integer simulatedRebootInterval) {
		this.simulatedRebootInterval = simulatedRebootInterval;
	}

	public Integer getSimulatedUpgradeInterval() {
		return simulatedUpgradeInterval;
	}

	public void setSimulatedUpgradeInterval(Integer simulatedUpgradeInterval) {
		this.simulatedUpgradeInterval = simulatedUpgradeInterval;
	}
}
