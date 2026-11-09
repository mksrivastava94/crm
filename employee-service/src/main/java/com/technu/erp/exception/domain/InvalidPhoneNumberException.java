package com.technu.erp.exception.domain;

public class InvalidPhoneNumberException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidPhoneNumberException(String message) {
        super(message);
    }
}
