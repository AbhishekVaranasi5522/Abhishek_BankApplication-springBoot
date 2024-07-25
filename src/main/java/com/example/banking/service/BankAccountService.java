package com.example.banking.service;

import com.example.banking.customExceptions.*;
import com.example.banking.dto.BankAccountRequestDto;
import com.example.banking.dto.BankAccountResponseDto;
import com.example.banking.dto.BankTransferRequestDto;
import com.example.banking.dto.BankTransferResponseDto;
import com.example.banking.model.Audit;
import com.example.banking.model.BankAccount;
import com.example.banking.model.Users;
import com.example.banking.repository.AuditRepository;
import com.example.banking.repository.BankAccountRepository;
import com.example.banking.repository.UserRepository;
import com.example.banking.utils.BranchCity;
import com.example.banking.utils.TransactionType;
import jakarta.transaction.InvalidTransactionException;
import jakarta.transaction.TransactionRolledbackException;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class BankAccountService {

    @Autowired
    BankAccountRepository bankAccountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuditRepository auditRepository;

    @Autowired
    ModelMapper modelMapper;

    public BankAccountResponseDto getBankAccountDetails(Long accountNumber) throws InvalidBankAccountNumberException
    {
        BankAccount bankAccount = bankAccountRepository.findById(accountNumber)
                .orElseThrow(InvalidBankAccountNumberException::new);

        return modelMapper.map(bankAccount,BankAccountResponseDto.class);
    }

    public BankAccountResponseDto createBankAccountForUser(BankAccountRequestDto bankAccountRequestDto) throws InvalidBankAccountRequestException, UserNotFoundException {
        Users user = userRepository.findById(bankAccountRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + bankAccountRequestDto.getUserId()));

        if (isInvalid(bankAccountRequestDto)) {
            throw new InvalidBankAccountRequestException("Invalid bank account creation request");
        }

        BankAccount newAccount = new BankAccount();
        newAccount.setUser(user);

        newAccount.setAccountStatus(bankAccountRequestDto.getAccountStatus());
        newAccount.setAccountType(bankAccountRequestDto.getAccountType());
        newAccount.setBranchCity(BranchCity.valueOf(bankAccountRequestDto.getBranchCity()));

        BankAccount bankAccount = bankAccountRepository.save(newAccount);

        //update users account list
        user.getBankAccounts().add(bankAccount);
        userRepository.save(user);

        //audit the transaction
        audit(bankAccountRequestDto, TransactionType.DEPOSIT);

        return modelMapper.map(bankAccount,BankAccountResponseDto.class);

    }

    private void audit(BankAccountRequestDto bankAccountRequestDto, TransactionType transactionType) {
        Audit audit = new Audit();

        audit.setAccountNumber(bankAccountRequestDto.getAccountNumber());
        audit.setUserId(bankAccountRequestDto.getUserId());
        audit.setTimestamp(new Date());
        audit.setTransactionAmount(bankAccountRequestDto.getAccountBalance());
        audit.setTransactionType(String.valueOf(transactionType));
        audit.setAccountNumber(bankAccountRequestDto.getAccountNumber());

        auditRepository.save(audit);
    }

    private boolean isInvalid(BankAccountRequestDto bankAccountRequestDto) {
        // Check if any of the required fields are null or empty
        return bankAccountRequestDto.getAccountType() == null || bankAccountRequestDto.getAccountType().isEmpty() ||
                bankAccountRequestDto.getAccountStatus() == null || bankAccountRequestDto.getAccountStatus().isEmpty() ||
                bankAccountRequestDto.getAccountBalance() == null;
    }

    @Transactional
    public BankAccountResponseDto updateBankAccountDetails(BankAccountRequestDto bankAccountRequestDto, Long accountNumber) throws AccountNotFoundException, InvalidBankAccountRequestException, BankAccountNotFoundException, UserNotFoundException {
        // Retrieve the user
        Users user = userRepository.findById(bankAccountRequestDto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + bankAccountRequestDto.getUserId()));

        // Find the bank account within the user's bank account list
        BankAccount bankAccountToUpdate = user.getBankAccounts().stream()
                .filter(account -> account.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElseThrow(() -> new BankAccountNotFoundException("Bank account not found with account number: " + accountNumber));

        // Update bank account details
        bankAccountToUpdate.setAccountType(bankAccountRequestDto.getAccountType());
        bankAccountToUpdate.setAccountStatus(bankAccountRequestDto.getAccountStatus());
        bankAccountToUpdate.setBranchCity(BranchCity.valueOf(bankAccountRequestDto.getBranchCity()));

        // Persist the changes
        userRepository.save(user);

        audit(bankAccountRequestDto,TransactionType.DEPOSIT);

        return modelMapper.map(bankAccountToUpdate,BankAccountResponseDto.class);
    }

    public BankAccountResponseDto deleteBankAccount(Long accountNumber) throws InvalidBankAccountNumberException
    {
        return bankAccountRepository.findById(accountNumber)
                .map(account -> {
                    bankAccountRepository.delete(account);
                    return modelMapper.map(account, BankAccountResponseDto.class);
                })
                .orElseThrow(InvalidBankAccountNumberException::new);
    }

}
