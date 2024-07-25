package com.example.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferResponseDto {

    private Long senderAccountNumber;

    private Long receiverAccountNumber;

    private Long transactionAmount;

    private String senderName;

    private String receiverName;

    private String errorMessage;

    public BankTransferResponseDto(String message) {
        this.setErrorMessage(message);
    }
}
