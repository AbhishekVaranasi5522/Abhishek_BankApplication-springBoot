package com.example.banking.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Audit {

    @Id
    @GeneratedValue
    private Integer auditId;

    private Date timestamp;

    @ManyToOne
    @JoinColumn(name = "senderAccountNumber", referencedColumnName = "accountNumber")
    private BankAccount senderAccount;

    @ManyToOne
    @JoinColumn(name = "receiverAccountNumber", referencedColumnName = "accountNumber")
    private BankAccount receiverAccount;

    private Long transactionAmount;

    private Integer userId;

    private Long accountNumber;

    private String transactionType;

}
