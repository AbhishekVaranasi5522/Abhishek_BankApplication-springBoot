package com.example.banking.controller;

import com.example.banking.customExceptions.UserNotFoundException;
import com.example.banking.dto.UserAndBankRequestDto;
import com.example.banking.dto.UserAndBankResponseDto;
import com.example.banking.dto.UserRequestDto;
import com.example.banking.dto.UserResponseDto;
import com.example.banking.model.Users;
import com.example.banking.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/users")
    public ResponseEntity<List<UserResponseDto>> getAllUser() throws UserNotFoundException {
        List<UserResponseDto> userList = null;
        try
        {
            userList = userService.getAllUsers();
            if (userList.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build(); // Return 204 No Content
            }
            return ResponseEntity.ok(userList);
        }
        catch (UserNotFoundException userException) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }

    @GetMapping("/userBankAccounts/{id}")
    public  ResponseEntity<UserAndBankResponseDto> getUserAllBankAccounts(@PathVariable Integer id)
    {
        try {
            UserAndBankResponseDto successResponse = userService.getUserAllBankAccounts(id);

            return ResponseEntity.ok(successResponse);

        }
        catch (Exception e)
        {
            UserAndBankResponseDto errorResponse = UserAndBankResponseDto.builder()
                    .errorMsg(e.getMessage())
                    .build();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> getUser(@PathVariable Integer id) throws UserNotFoundException {
        try
        {
            UserResponseDto user = userService.getUser(id);

            return ResponseEntity.ok(user);
        }
        catch (UserNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new UserResponseDto(e.getMessage()));
        }
    }


    @PostMapping("/users")
    public ResponseEntity<UserResponseDto> createUser(@RequestBody UserRequestDto userRequestDto)
    {
        try
        {
            return ResponseEntity.ok().body(userService.createUser(userRequestDto));
        }
        catch (Exception e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new UserResponseDto("Error creating user: " + e.getMessage()));

        }
    }

    @PutMapping("/users/{id}")
    public ResponseEntity<UserResponseDto> updateUser(@RequestBody UserRequestDto userRequestDto, @PathVariable Integer id) throws UserNotFoundException
    {
        try
        {
            return ResponseEntity.ok().body(userService.updateUser(userRequestDto,id));
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponseDto("Error creating user: " + e.getMessage()));
        }
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Integer id)
    {
        String message ="";
        try
        {
            if(userService.deleteUser(id))
                message = "User with id : "+id + " deleted successfully";
            return ResponseEntity.ok().body(message);
        }
        catch (UserNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }

}
