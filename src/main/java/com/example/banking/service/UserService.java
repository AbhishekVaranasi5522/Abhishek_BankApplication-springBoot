package com.example.banking.service;

import com.example.banking.customExceptions.UserNotFoundException;
import com.example.banking.dto.BankAccountResponseDto;
import com.example.banking.dto.UserAndBankResponseDto;
import com.example.banking.dto.UserRequestDto;
import com.example.banking.dto.UserResponseDto;
import com.example.banking.model.BankAccount;
import com.example.banking.model.Users;
import com.example.banking.repository.UserRepository;
import com.example.banking.utils.BranchCity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    ModelMapper modelMapper;

    public List<UserResponseDto> getAllUsers() throws UserNotFoundException{

        List<Users> usersList;
        List<UserResponseDto> userResponseList;
        usersList = userRepository.findAll();
        userResponseList = mapUsersToUserResponseDto(usersList);
        if (usersList.isEmpty())
            throw new UserNotFoundException("No users Exist");

        return userResponseList;
    }

    public List<UserResponseDto> mapUsersToUserResponseDto(List<Users> usersList) {
        return usersList.stream()
                .map(this::mapUserToUserResponseDto)
                .collect(Collectors.toList());
    }

    private UserResponseDto mapUserToUserResponseDto(Users user) {
        return modelMapper.map(user, UserResponseDto.class);
    }

    public  UserResponseDto createUser(UserRequestDto userRequestDto) throws Exception
    {
        try
        {
            Users createdUser = userRepository.save(modelMapper.map(userRequestDto, Users.class));
            return modelMapper.map(createdUser, UserResponseDto.class);
        }
        catch (Exception e) {
            // Log the exception or handle it as needed
            throw new RuntimeException("Failed to create user: " + e.getMessage(), e);
        }
    }

    public UserResponseDto updateUser(UserRequestDto userRequestDto,Integer userId) throws UserNotFoundException
    {
        Users existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("No user found with ID: " + userId));

        existingUser.setEmail(userRequestDto.getEmail());
        existingUser.setContactNumber(userRequestDto.getContactNumber());
        existingUser.setFirstName(userRequestDto.getFirstName());
        existingUser.setLastName(userRequestDto.getLastName());

        Users updatedUser = userRepository.save(existingUser);

        return modelMapper.map(updatedUser, UserResponseDto.class);
    }

    public Boolean deleteUser(Integer userId) throws UserNotFoundException {
        Optional<Users> user = userRepository.findById(userId);

        if (user.isPresent())
            userRepository.deleteById(userId);
        else
            throw new UserNotFoundException("User not found with id :"+userId);
        return true;
    }

    public UserResponseDto getUser(Integer userId) throws UserNotFoundException{
        Optional<Users> user = userRepository.findById(userId);
        if (user.isPresent())
            return modelMapper.map(user.get(),UserResponseDto.class);
        else
            throw new UserNotFoundException("No User found with userId : "+userId);
    }

    public UserAndBankResponseDto getUserAllBankAccounts(Integer userId) throws UserNotFoundException, Exception{
        // Check if the user exists
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + userId));

        // Map user and bank accounts to DTOs
        UserResponseDto userResponseDto = UserResponseDto.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .contactNumber(user.getContactNumber())
                .email(user.getEmail())
                .build();

        List<BankAccountResponseDto> bankAccountResponseDtos = user.getBankAccounts().stream()
                .map(this::mapToBankAccountResponseDto)
                .collect(Collectors.toList());

        // Create and return the response DTO
        return UserAndBankResponseDto.builder()
                .userResponseDto(userResponseDto)
                .bankAccountResponseDto(bankAccountResponseDtos)
                .build();
    }

    private BankAccountResponseDto mapToBankAccountResponseDto(BankAccount bankAccount) {
        return new BankAccountResponseDto(
                bankAccount.getAccountNumber(),
                bankAccount.getAccountType(),
                bankAccount.getAccountStatus(),
                bankAccount.getAccountBalance(),
                null ,// Assuming there's no error message in this case,
                bankAccount.getBranchCode(),
                String.valueOf(bankAccount.getBranchCity())
        );
    }
}
