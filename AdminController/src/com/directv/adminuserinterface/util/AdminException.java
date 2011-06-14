/*
 * Author : Meiy
 */
package com.directv.adminuserinterface.util;

// TODO: Auto-generated Javadoc
/**
 * The Class AdminException.
 */
public class AdminException extends Exception {

	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/**
	 * Instantiates a new admin exception.
	 *
	 * @param message the message
	 */
	public AdminException(String message) {
		super(message);
	}

	/**
	 * Instantiates a new admin exception.
	 */
	public AdminException() {

	}

	/**
	 * Overridden Method.
	 * 
	 * @return the message
	 */
	public String getMessage() {
		String message = super.getMessage();
		return message;
	}
}
