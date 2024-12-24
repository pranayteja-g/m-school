package com.raiden.mchool.custom_exceptions;

public class UsernameAlreadyTakenException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsernameAlreadyTakenException(String message) {
		super(message);
	}
}
