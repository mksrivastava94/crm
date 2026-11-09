package com.technu.erp.exception.domain;

public class EmployeeExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public EmployeeExistException(String message) {
        super(message);
    }
}
