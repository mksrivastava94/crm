package com.technu.erp.exception.domain;

public class ProfileExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public ProfileExistException(String message) {
        super(message);
    }
}
