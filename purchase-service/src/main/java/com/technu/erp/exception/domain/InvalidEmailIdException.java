package com.technu.erp.exception.domain;

public class InvalidEmailIdException extends Exception {

	private static final long serialVersionUID = 1L;

	public InvalidEmailIdException(String message) {
		super(message);
	}
}
