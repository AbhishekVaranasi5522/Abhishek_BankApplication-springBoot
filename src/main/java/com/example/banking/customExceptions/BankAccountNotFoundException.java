package com.example.banking.customExceptions;

public class BankAccountNotFoundException extends Exception {
    public BankAccountNotFoundException() {
        super("-->Bank account not found<--");
    }

    public BankAccountNotFoundException(String message) {
        super(message);
    }
}
