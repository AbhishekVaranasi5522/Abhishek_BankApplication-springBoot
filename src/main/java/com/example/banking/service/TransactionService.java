package com.example.banking.service;

import com.example.banking.customExceptions.BankAccountNotFoundException;
import com.example.banking.customExceptions.InsufficientBalanceException;
import com.example.banking.dto.BankAccountResponseDto;
import com.example.banking.dto.BankTransferRequestDto;
import com.example.banking.dto.BankTransferResponseDto;
import com.example.banking.dto.BankWithdrawRequestDto;
import com.example.banking.model.BankAccount;
import com.example.banking.model.Users;
import com.example.banking.repository.AuditRepository;
import com.example.banking.repository.BankAccountRepository;
import com.example.banking.repository.UserRepository;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.TransactionRolledbackException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditRepository auditRepository;

    @Transactional
    public BankTransferResponseDto transferMoney(BankTransferRequestDto bankTransferRequestDto) throws BankAccountNotFoundException, InsufficientBalanceException, InvalidTransactionException, TransactionRolledbackException {

        if (bankTransferRequestDto.getTransactionAmount() <= 0) {
            throw new InvalidTransactionException("Invalid Transaction Amount"); // Invalid transaction amount
        }

        boolean transferred = false;

        BankAccount senderAccount = bankAccountRepository.findById(bankTransferRequestDto.getSenderAccountNumber()).orElseThrow(BankAccountNotFoundException::new);

        BankAccount receiverAccount = bankAccountRepository.findById(bankTransferRequestDto.getReceiverAccountNumber()).orElseThrow(BankAccountNotFoundException::new);

        Boolean deducted = deductMoneyFromSenderAccount(senderAccount,bankTransferRequestDto);

        if(deducted)
        {
            addFundsToReceiverAccount(receiverAccount, bankTransferRequestDto);
            transferred = true;
        }

        if(!transferred)
            throw new TransactionRolledbackException("Transaction Rolled back");

        return getBankTransferResponseDto(bankTransferRequestDto);
    }

    private BankTransferResponseDto getBankTransferResponseDto(BankTransferRequestDto bankTransferRequestDto) {
        BankTransferResponseDto responseDto = new BankTransferResponseDto();
        responseDto.setTransactionAmount(bankTransferRequestDto.getTransactionAmount());

        Users sender = userRepository.findByAccountNumber(bankTransferRequestDto.getSenderAccountNumber());
        String senderName = sender.getFirstName() + " " + sender.getLastName();
        responseDto.setReceiverName(senderName);

        Users receiver = userRepository.findByAccountNumber(bankTransferRequestDto.getReceiverAccountNumber());
        String receiverName = receiver.getFirstName() + " " + receiver.getLastName();
        responseDto.setReceiverName(receiverName);

        responseDto.setSenderAccountNumber(bankTransferRequestDto.getSenderAccountNumber());
        responseDto.setReceiverAccountNumber(bankTransferRequestDto.getReceiverAccountNumber());
        return responseDto;
    }

    private void addFundsToReceiverAccount(BankAccount receiverAccount, BankTransferRequestDto bankTransferRequestDto){

        Long accountBalance = receiverAccount.getAccountBalance();

        Long updatedAccountBalance = accountBalance + bankTransferRequestDto.getTransactionAmount();
        receiverAccount.setAccountBalance(updatedAccountBalance);
        bankAccountRepository.save(receiverAccount);

    }

    private Boolean deductMoneyFromSenderAccount(BankAccount senderAccount, BankTransferRequestDto bankTransferRequestDto) throws InsufficientBalanceException {

        Long accountBalance = senderAccount.getAccountBalance();

        long updatedAccountBalance = accountBalance - bankTransferRequestDto.getTransactionAmount();

        if (updatedAccountBalance < 0)
            throw new InsufficientBalanceException();

        senderAccount.setAccountBalance(updatedAccountBalance);

        bankAccountRepository.save(senderAccount);

        return true;
    }

//    @Transactional
//    public BankAccountResponseDto withdrawMoney(BankWithdrawRequestDto bankWithdrawRequestDto) throws BankAccountNotFoundException, InsufficientBalanceException, InvalidTransactionException, TransactionRolledbackException
//    {
//
//        if (bankWithdrawRequestDto.getWithdrawAmount() <= 0) {
//            throw new InvalidTransactionException("Invalid Transaction Amount"); // Invalid transaction amount
//        }
//
//        boolean withdrawSuccessfull = false;
//
//        BankAccount bankAccount = bankAccountRepository.findById(bankWithdrawRequestDto.getAccountNumber()).orElseThrow(throw new BankAccountNotFoundException());
//
//        Long updatedAccountBalance = bankAccount.getAccountBalance() - bankWithdrawRequestDto.getWithdrawAmount();
//
//        BankAccount updatedBankAccount;
//
//        if(updatedAccountBalance < 0)
//        {
//            throw new InsufficientBalanceException();
//        }
//
//        else
//        {
//            bankAccount.setAccountBalance(updatedAccountBalance);
//            updatedBankAccount = bankAccountRepository.save(bankAccount);
//            withdrawSuccessfull = true;
//        }
//
//        if(!withdrawSuccessfull)
//        {
//            throw new TransactionRolledbackException("Transaction Rolled back");
//        }
//
//        return getBankAccountResponseDto(updatedBankAccount);
//    }

    private BankAccountResponseDto getBankAccountResponseDto(BankAccount updatedBankAccount) {

        BankAccountResponseDto bankAccountResponseDto = new BankAccountResponseDto();
        bankAccountResponseDto.setAccountNumber(updatedBankAccount.getAccountNumber());
        bankAccountResponseDto.setAccountType(updatedBankAccount.getAccountType());
        bankAccountResponseDto.setAccountBalance(updatedBankAccount.getAccountBalance());
        bankAccountResponseDto.setBranchCity(String.valueOf(updatedBankAccount.getBranchCity()));
        bankAccountResponseDto.setBranchCode(updatedBankAccount.getBranchCode());

        return bankAccountResponseDto;
    }
}
