package com.example.banking.customExceptions;

public class AccountNotFoundException extends Exception{

    public AccountNotFoundException()
    {
        super("No Account found");
    }
}
