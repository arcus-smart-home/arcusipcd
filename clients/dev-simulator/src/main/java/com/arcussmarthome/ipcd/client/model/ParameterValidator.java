package com.arcussmarthome.ipcd.client.model;

public interface ParameterValidator {

	public void validate(Object value) throws ValidationException;
}
