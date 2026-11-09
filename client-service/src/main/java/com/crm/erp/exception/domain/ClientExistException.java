package com.crm.erp.exception.domain;

public class ClientExistException extends Exception {
    private static final long serialVersionUID = 1L;

    public ClientExistException(String message) {
        super(message);
    }
}
