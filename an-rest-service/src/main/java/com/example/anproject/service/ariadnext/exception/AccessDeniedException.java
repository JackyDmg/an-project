package com.example.anproject.service.ariadnext.exception;

import org.springframework.core.NestedRuntimeException;

/**
 * The Class AccessDeniedException.
 */
public class AccessDeniedException extends NestedRuntimeException {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 8075291575523821087L;

	/**
	 * Instantiates a new access denied exception.
	 */
	public AccessDeniedException() {
		super("AccessDeniedException");
	}

	/**
	 * Instantiates a new access denied exception.
	 *
	 * @param message the message
	 */
	public AccessDeniedException(String message) {
		super(message);
	}

}
