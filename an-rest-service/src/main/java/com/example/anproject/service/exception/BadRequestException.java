package com.example.anproject.service.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * The Class BadRequestException.
 */
public class BadRequestException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2377280797901199784L;

	/**
	 * Instantiates a new bad request exception.
	 */
	public BadRequestException() {
		super("BadRequestException");
	}

	/**
	 * Instantiates a new bad request exception.
	 *
	 * @param message the message
	 */
	public BadRequestException(String message) {
		super(message);
	}

}
