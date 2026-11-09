package com.technu.erp.exception.domain;

public class InvalidDataInputException extends Exception{

    private static final long serialVersionUID = 1L;
    public InvalidDataInputException(String message) {
        super(message);
    }
}
