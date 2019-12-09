package com.arcussmarthome.ipcd.valid;

import java.util.ArrayList;
import java.util.List;

public class ValidationResults {
	
	private List<String> infos = new ArrayList<String>();
	private List<String> warnings = new ArrayList<String>();
	private List<String> errors = new ArrayList<String>();
	
	public List<String> getInfos() {
		return infos;
	}
	
	public List<String> getWarnings() {
		return warnings;
	}
	
	public List<String> getErrors() {
		return errors;
	}
	
	public boolean hasErrors() {
		return this.errors.size() > 0;
	}
	
	public boolean hasWarnings() {
		return this.warnings.size() > 0;
	}
	
	void addError(String error) {
		this.errors.add(error);
	}
	void addWarning(String error) {
		this.warnings.add(error);
	}
	void addInfo(String error) {
		this.infos.add(error);
	}
	
	
}
