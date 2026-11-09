package com.crm.erp.exception.domain;

public class ProductExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public ProductExistException(String message) {
        super(message);
    }
}
