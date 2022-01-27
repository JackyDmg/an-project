package com.example.anproject.service.ariadnext.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * The Class GenericException.
 */
public class GenericException extends NestedRuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8075291575523821087L;

	/**
	 * Instantiates a new generic exception.
	 */
	public GenericException() {
		super("GenericException");
	}

	/**
	 * Instantiates a new generic exception.
	 *
	 * @param message the message
	 */
	public GenericException(String message) {
		super(message);
	}

}
