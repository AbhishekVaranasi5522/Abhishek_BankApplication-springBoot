package com.example.banking.controller;

import com.example.banking.customExceptions.BankAccountNotFoundException;
import com.example.banking.customExceptions.InsufficientBalanceException;
import com.example.banking.dto.BankAccountResponseDto;
import com.example.banking.dto.BankTransferRequestDto;
import com.example.banking.dto.BankTransferResponseDto;
import com.example.banking.dto.BankWithdrawRequestDto;
import com.example.banking.service.TransactionService;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.TransactionRolledbackException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {

    @Autowired
    TransactionService transactionService;

    @PutMapping("/transfer")
    public ResponseEntity<BankTransferResponseDto> transferMoney(@RequestBody BankTransferRequestDto bankTransferRequestDto)
    {
        try
        {
            BankTransferResponseDto successResponse = transactionService.transferMoney(bankTransferRequestDto);

            return ResponseEntity.ok(successResponse);
        }
        catch (BankAccountNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new BankTransferResponseDto(e.getMessage()));
        }
        catch (InsufficientBalanceException | InvalidTransactionException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new BankTransferResponseDto(e.getMessage()));
        }
        catch (TransactionRolledbackException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BankTransferResponseDto(e.getMessage()));
        }
        catch (Exception e) {
            // Catch any other unexpected exceptions and handle them
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new BankTransferResponseDto("An unexpected error occurred"));
        }
    }

//    @PutMapping("/withdraw")
//    public ResponseEntity<BankAccountResponseDto> withdrawMoney(@RequestBody BankWithdrawRequestDto bankWithdrawRequestDto)
//    {
//        try
//        {
//            return ResponseEntity.ok(transactionService.withdrawMoney(bankWithdrawRequestDto));
//
//        }
//        catch ()
//        {
//
//        } catch (BankAccountNotFoundException e) {
//            throw new RuntimeException(e);
//        } catch (InsufficientBalanceException e) {
//            throw new RuntimeException(e);
//        } catch (InvalidTransactionException e) {
//            throw new RuntimeException(e);
//        } catch (TransactionRolledbackException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
