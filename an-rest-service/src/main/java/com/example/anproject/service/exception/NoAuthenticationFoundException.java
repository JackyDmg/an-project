package com.example.anproject.service.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * The Class NoAuthenticationFoundException.
 */
public class NoAuthenticationFoundException extends NestedRuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 2377280797901199784L;

	/**
	 * Instantiates a new no authentication found exception.
	 */
	public NoAuthenticationFoundException() {
		super("NoAuthenticationFoundException");
	}

	/**
	 * Instantiates a new no authentication found exception.
	 *
	 * @param message the message
	 */
	public NoAuthenticationFoundException(String message) {
		super(message);
	}

}
