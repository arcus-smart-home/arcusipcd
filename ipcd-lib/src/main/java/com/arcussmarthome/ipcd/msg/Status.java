package com.arcussmarthome.ipcd.msg;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Status {

	public enum Result { success, fail, error };
	
	private Result result;
	private List<String> messages;
	
	public Status() {
		this.result = Result.success;
		this.messages = new ArrayList<String>();
	}
	
	public Status(Result result, String... messages) {
		this.result = result;
		this.messages = Arrays.asList(messages);
	}
	
	public Result getResult() {
		return result;
	}
	public void setResult(Result result) {
		this.result = result;
	}
	public List<String> getMessages() {
		return messages;
	}
	public void setMessages(List<String> messages) {
		this.messages = messages;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((messages == null) ? 0 : messages.hashCode());
		result = prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
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
		Status other = (Status) obj;
		if (messages == null) {
			if (other.messages != null)
				return false;
		} else if (!messages.equals(other.messages))
			return false;
		if (result != other.result)
			return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Status [result=" + result + ", messages=" + messages + "]";
	}
	
	
}
