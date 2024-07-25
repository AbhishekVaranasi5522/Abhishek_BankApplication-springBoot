package com.example.banking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class UserAndBankResponseDto {

    UserResponseDto userResponseDto;

    List<BankAccountResponseDto> bankAccountResponseDto;

    private String errorMsg;

}
