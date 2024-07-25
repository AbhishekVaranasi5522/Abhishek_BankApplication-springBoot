package com.example.banking.customExceptions;

public class InvalidBankAccountRequestException extends RuntimeException {
    public InvalidBankAccountRequestException(String message) {
        super(message);
    }
}
