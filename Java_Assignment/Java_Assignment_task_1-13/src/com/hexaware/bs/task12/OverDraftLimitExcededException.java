package com.hexaware.bs.task12;

public class OverDraftLimitExcededException extends Exception {
    private static final long serialVersionUID = 3L;
    
    public OverDraftLimitExcededException(String message) {
        super(message);
    }
}