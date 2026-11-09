package com.technu.erp.exception.domain;

public class MaterialExistException extends Exception {

    private static final long serialVersionUID = 1L;

    public MaterialExistException(String message) {
        super(message);
    }
}
