package com.example.banking.dto;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {

    private Integer userId;

    private String firstName;

    private String lastName;

    private Long contactNumber;

    private String email;

    private String errorMessage;

    public UserResponseDto(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
