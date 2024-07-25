package com.example.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankTransferRequestDto {

    private Long senderAccountNumber;

    private Long receiverAccountNumber;

    private Long transactionAmount;

}
