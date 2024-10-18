package com.hexaware.bs.task12;

public class InvalidAccountException extends Exception {
    private static final long serialVersionUID = 2L;
    
    public InvalidAccountException(String message) {
        super(message);
    }
}