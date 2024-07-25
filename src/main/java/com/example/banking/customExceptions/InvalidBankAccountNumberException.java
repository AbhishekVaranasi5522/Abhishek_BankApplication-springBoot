package com.example.banking.customExceptions;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


public class InvalidBankAccountNumberException extends Exception{

    public InvalidBankAccountNumberException() {
        super("Invalid Bank account number");
    }

}
