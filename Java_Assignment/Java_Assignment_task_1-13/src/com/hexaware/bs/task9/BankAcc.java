package com.hexaware.bs.task9;

public abstract class BankAcc {
    private int accountNumber;
    private String customerName;
    private double balance;

    public BankAcc() {
        super();
    }

    public BankAcc(int accountNumber, String customerName, double balance) {
        super();
        this.accountNumber = accountNumber;
        this.customerName = customerName;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public void printAccountDetails() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Customer Name: " + customerName);
        System.out.println("Balance: " + balance);
    }

    public abstract void deposit(float amount);
    public abstract void withdraw(float amount);
    public abstract void calculateInterest();
}