package com.example.banking.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRequestDto {

    private String firstName;

    private String lastName;

    private Long contactNumber;

    private String Email;

}
