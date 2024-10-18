package com.hexaware.bs.task12;

public abstract class Account {
    private int accountNumber;
    protected double balance;
    private String accountType;

    public Account(int accountNumber, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public String getAccountType() {
        return accountType;
    }

    abstract void withdraw(double amount) throws InsufficientFundException, OverDraftLimitExcededException;
}