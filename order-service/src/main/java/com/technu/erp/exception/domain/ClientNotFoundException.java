package com.technu.erp.exception.domain;

public class ClientNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    public ClientNotFoundException(String message) {
        super(message);
    }
}
