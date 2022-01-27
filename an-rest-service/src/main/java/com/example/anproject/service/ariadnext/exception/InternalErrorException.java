package com.example.anproject.service.ariadnext.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * The Class InternalErrorException.
 */
public class InternalErrorException extends NestedRuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5372824209604071368L;

	/**
	 * Instantiates a new internal error exception.
	 */
	public InternalErrorException() {
		super("InternalErrorException");
	}

	/**
	 * Instantiates a new internal error exception.
	 *
	 * @param message the message
	 */
	public InternalErrorException(String message) {
		super(message);
	}

}
