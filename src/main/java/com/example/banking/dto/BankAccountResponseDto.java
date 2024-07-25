package com.example.banking.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BankAccountResponseDto {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long accountNumber;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountType;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String accountStatus;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Long accountBalance;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String errorMsg;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String branchCode;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String branchCity;
}
