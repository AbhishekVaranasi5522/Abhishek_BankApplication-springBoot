package com.example.banking.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class BankAccountRequestDto {

    private Integer userId;

    private Long accountNumber;

    private String accountType;

    private String accountStatus;

    private Long accountBalance;

    private String branchCity;
}
