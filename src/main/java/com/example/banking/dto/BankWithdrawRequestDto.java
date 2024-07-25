package com.example.banking.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankWithdrawRequestDto {

    private Long accountNumber;

    private String accountType;

    private String firstName;

    private String lastName;

    private Long withdrawAmount;
}
