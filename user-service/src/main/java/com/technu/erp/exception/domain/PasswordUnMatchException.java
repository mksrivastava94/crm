package com.technu.erp.exception.domain;

    public class PasswordUnMatchException extends Exception {

        private static final long serialVersionUID = 1L;

        public PasswordUnMatchException(String message) {
            super(message);
        }
    }

