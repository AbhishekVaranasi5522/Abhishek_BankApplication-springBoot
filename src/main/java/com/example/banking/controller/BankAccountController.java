package com.example.banking.controller;

import com.example.banking.customExceptions.AccountNotFoundException;
import com.example.banking.customExceptions.InvalidBankAccountNumberException;
import com.example.banking.customExceptions.InvalidBankAccountRequestException;
import com.example.banking.dto.BankAccountRequestDto;
import com.example.banking.dto.BankAccountResponseDto;
import com.example.banking.dto.UserAndBankResponseDto;
import com.example.banking.service.BankAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
public class BankAccountController {

    @Autowired
    BankAccountService bankAccountService;



    @GetMapping("/bankAccount/{accNum}")
    public ResponseEntity<BankAccountResponseDto> getBankAccountDetails(@PathVariable Long accNum) {
        try {
            BankAccountResponseDto bankAccountResponseDto = bankAccountService.getBankAccountDetails(accNum);
            return ResponseEntity.ok(bankAccountResponseDto);
        }
        catch (Exception e){
            BankAccountResponseDto errorResponse = new BankAccountResponseDto();
            errorResponse.setErrorMsg(e.getMessage());

            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(errorResponse);
        }
    }


    @PostMapping("/bankAccount")
    public ResponseEntity<BankAccountResponseDto> createBankAccountForUser(@RequestBody BankAccountRequestDto bankAccountRequestDto) throws InvalidBankAccountRequestException
    {
        try{
            BankAccountResponseDto successResponse = bankAccountService.createBankAccountForUser(bankAccountRequestDto);
            return ResponseEntity.ok(successResponse);
        }
        catch (InvalidBankAccountRequestException e) {
            BankAccountResponseDto errorResponse = new BankAccountResponseDto();
            errorResponse.setErrorMsg(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse); // Return 400 Bad Request
        }
        catch (Exception e)
        {
            BankAccountResponseDto errorResponse = new BankAccountResponseDto();
            errorResponse.setErrorMsg(e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @PutMapping("/bankAccount/{accNum}")
    public ResponseEntity<BankAccountResponseDto> updateBankAccountDetails(@RequestBody BankAccountRequestDto bankAccountRequestDto, @PathVariable Long accNum) throws InvalidBankAccountRequestException,AccountNotFoundException
    {
        System.out.println("hii");
        try{
            BankAccountResponseDto successResponse = bankAccountService.updateBankAccountDetails(bankAccountRequestDto,accNum);
            return ResponseEntity.ok(successResponse);
        }
        catch (AccountNotFoundException | InvalidBankAccountRequestException e)
        {
            BankAccountResponseDto errorResponse = new BankAccountResponseDto();
            errorResponse.setErrorMsg(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse); // Return 400 Bad Request
        }

        catch (Exception e)
        {
            BankAccountResponseDto errorResponse = new BankAccountResponseDto();
            errorResponse.setErrorMsg(e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

    @DeleteMapping("/bankAccount/{accNum}")
    public ResponseEntity<BankAccountResponseDto> deleteBankAccount(@PathVariable Long accNum) throws InvalidBankAccountNumberException
    {
        try{
            BankAccountResponseDto successResponse = bankAccountService.deleteBankAccount(accNum);
            return ResponseEntity.ok(successResponse);
        }
        catch ( InvalidBankAccountNumberException e)
        {
            BankAccountResponseDto errorResponse = new BankAccountResponseDto();
            errorResponse.setErrorMsg(e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse); // Return 400 Bad Request
        }

        catch (Exception e)
        {
            BankAccountResponseDto errorResponse = new BankAccountResponseDto();
            errorResponse.setErrorMsg(e.getMessage());

            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }

}
