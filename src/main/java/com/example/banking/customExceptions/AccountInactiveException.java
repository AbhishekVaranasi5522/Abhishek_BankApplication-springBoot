package com.example.banking.customExceptions;

public class AccountInactiveException extends Exception{

    public AccountInactiveException() {
        super("Account is inactive");
    }

    public AccountInactiveException(String message) {
        super(message);
    }

    public AccountInactiveException(String message, Throwable cause) {
        super(message, cause);
    }

}
