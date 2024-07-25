package com.example.banking.utils;

public enum BranchCity
{

    HYDERABAD("HOO1"),
    CHENNAI("C002"),
    KOLKATA("K003"),
    MUMBAI("M004");

    private final String branchCode;

    BranchCity(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getBranchCode() {
        return branchCode;
    }
}
