package com.arcussmarthome.ipcd.msg;

import java.util.List;

public class DeviceInfo {

	private String fwver;
	private ConnectionType connection;
	private String connectUrl;
	private Integer uptime;
	private List<ActionType> actions;
	private List<CommandType> commands;
	
	public String getFwver() {
		return fwver;
	}
	public void setFwver(String fwver) {
		this.fwver = fwver;
	}
	public ConnectionType getConnection() {
		return connection;
	}
	public void setConnection(ConnectionType connection) {
		this.connection = connection;
	}
	public String getConnectUrl() {
		return connectUrl;
	}
	public void setConnectUrl(String connectUrl) {
		this.connectUrl = connectUrl;
	}
	public Integer getUptime() {
		return uptime;
	}
	public void setUptime(Integer uptime) {
		this.uptime = uptime;
	}
	public List<ActionType> getActions() {
		return actions;
	}
	public void setActions(List<ActionType> actions) {
		this.actions = actions;
	}
	public List<CommandType> getCommands() {
		return commands;
	}
	public void setCommands(List<CommandType> commands) {
		this.commands = commands;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((actions == null) ? 0 : actions.hashCode());
		result = prime * result
				+ ((commands == null) ? 0 : commands.hashCode());
		result = prime * result
				+ ((connection == null) ? 0 : connection.hashCode());
		result = prime * result
				+ ((connectUrl == null) ? 0 : connectUrl.hashCode());
		result = prime * result + ((fwver == null) ? 0 : fwver.hashCode());
		result = prime * result
				+ ((uptime == null) ? 0 : uptime.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DeviceInfo other = (DeviceInfo) obj;
		if (actions == null) {
			if (other.actions != null)
				return false;
		} else if (!actions.equals(other.actions))
			return false;
		if (commands == null) {
			if (other.commands != null)
				return false;
		} else if (!commands.equals(other.commands))
			return false;
		if (connection != other.connection)
			return false;
		if (connectUrl == null) {
			if (other.connectUrl != null)
				return false;
		} else if (!connectUrl.equals(other.connectUrl))
			return false;
		if (fwver == null) {
			if (other.fwver != null)
				return false;
		} else if (!fwver.equals(other.fwver))
			return false;
		if (uptime == null) {
			if (other.uptime != null)
				return false;
		} else if (!uptime.equals(other.uptime))
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "DeviceInfo [fwver=" + fwver + ", connection=" + connection
				+ ", connectUrl=" + connectUrl + ", uptime=" + uptime
				+ ", actions=" + actions + ", commands=" + commands + "]";
	}
	
}
