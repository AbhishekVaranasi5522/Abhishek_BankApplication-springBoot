package com.example.banking.model;

import com.example.banking.utils.BranchCity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
//    , generator = "numeric_generator")
//    @GenericGenerator(
//            name = "numeric_generator",
//            strategy = "com.example.banking.utilNumericGenerator"
//    )
    private Long accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private Users user;

    private String accountType;

    private String accountStatus;

    private Long accountBalance = 0L;

    private String branchCode;

    @Enumerated(EnumType.STRING)
    private BranchCity branchCity;

    public void setBranchCity(BranchCity branchCity) {
        if (branchCity != null) {
            this.branchCity = branchCity;
            this.branchCode = branchCity.getBranchCode();
        } else {
            // Set default values or throw an exception if branch city is null
            this.branchCity = null;
            this.branchCode = null;
        }
    }

}
