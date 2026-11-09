package com.technu.erp.exception.domain;

public class ProfileNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProfileNotFoundException(String message) {
        super(message);
    }
}
