package com.jsoft.oa.core.exception;

public class OAException extends RuntimeException {

	private static final long serialVersionUID = 4518854465905100143L;

	public OAException() {
	}

	public OAException(String message) {
		super(message);
	}

	public OAException(Throwable cause) {
		super(cause);
	}

	public OAException(String message, Throwable cause) {
		super(message, cause);
	}

	public OAException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

}