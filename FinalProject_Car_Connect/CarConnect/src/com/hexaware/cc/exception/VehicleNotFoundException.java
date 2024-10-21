package com.hexaware.cc.exception;

public class VehicleNotFoundException extends Exception {
    public VehicleNotFoundException() {
        super();
    }

    public VehicleNotFoundException(String message) {
        super(message);
    }

    public VehicleNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}