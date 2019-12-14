package com.arcussmarthome.ipcd.ser;

public class InvalidMessageException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8165078522147334346L;

	public InvalidMessageException() {
		super();
	}

	public InvalidMessageException(String message, Throwable cause) {
		super(message, cause);
	}

	public InvalidMessageException(String message) {
		super(message);
	}

	public InvalidMessageException(Throwable cause) {
		super(cause);
	}

	

}
