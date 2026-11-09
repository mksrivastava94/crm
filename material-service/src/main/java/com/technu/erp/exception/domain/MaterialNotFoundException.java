package com.technu.erp.exception.domain;

public class MaterialNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    public MaterialNotFoundException(String message) {
        super(message);
    }
}
