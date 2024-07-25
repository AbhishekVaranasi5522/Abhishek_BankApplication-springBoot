package com.example.banking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserAndBankRequestDto {

    UserRequestDto userRequestDto;

    List<BankAccountRequestDto> bankAccountRequestDtos;
}
