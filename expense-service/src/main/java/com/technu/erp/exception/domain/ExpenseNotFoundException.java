package com.technu.erp.exception.domain;

public class ExpenseNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;

    public ExpenseNotFoundException(String message) {
        super(message);
    }
}
