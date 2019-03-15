package com.iris.ipcd.client.model;

public interface ParameterValidator {

	public void validate(Object value) throws ValidationException;
}
