package com.crm.erp.exception.domain;

public class InvalidOrgIdOrEmptyException extends Exception {

    private static final long serialVersionUID = 1L;

    public InvalidOrgIdOrEmptyException(String message) {
        super(message);
    }
}
