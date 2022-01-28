package com.example.anproject.service.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * The Class NotAllowedException.
 */
public class NotAllowedException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2377280797901199784L;

	/**
	 * Instantiates a new not allowed exception.
	 */
	public NotAllowedException() {
		super("NotAllowedException");
	}

	/**
	 * Instantiates a new bad request exception.
	 *
	 * @param message the message
	 */
	public NotAllowedException(String message) {
		super(message);
	}

}
