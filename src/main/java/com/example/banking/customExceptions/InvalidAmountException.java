package com.example.banking.customExceptions;

public class InvalidAmountException extends Exception {
    public InvalidAmountException() {
        super("Invalid amount");
    }

    public InvalidAmountException(String message) {
        super(message);
    }

    public InvalidAmountException(String message, Throwable cause) {
        super(message, cause);
    }
}
