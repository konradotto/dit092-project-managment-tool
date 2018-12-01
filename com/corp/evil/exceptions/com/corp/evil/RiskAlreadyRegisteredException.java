package com.corp.evil;

public class RiskAlreadyRegisteredException extends Exception {
    public RiskAlreadyRegisteredException(String message) {
        super(message);
    }
}
