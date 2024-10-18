package com.hexaware.bs.task13;

import java.util.ArrayList;
import java.util.List;

public class HMBankList {
    private List<BankAccount> accounts;

    public HMBankList() {
        this.accounts = new ArrayList<>();
    }

    public void createAccount(int accountId, String accountType, double balance, String customerName) {
        // Check for duplicate account
        if (findAccount(accountId) != null) {
            System.out.println("Account already exists with ID: " + accountId);
            return;
        }
        
        BankAccount account = new BankAccount(accountId, accountType, balance, customerName);
        accounts.add(account);
        System.out.println("Account created successfully for " + customerName);
    }

    public void listAccounts() {
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
            return;
        }
        
        for (BankAccount account : accounts) {
            System.out.println(account);
        }
    }

    public void displayBalance(int accountId) {
        BankAccount account = findAccount(accountId);
        if (account != null) {
            System.out.println("Balance for Account " + accountId + ": $" + account.getBalance());
        } else {
            System.out.println("Account not found: " + accountId);
        }
    }

    public void withdraw(int accountId, double amount) {
        BankAccount account = findAccount(accountId);
        if (account == null) {
            System.out.println("Account not found: " + accountId);
            return;
        }
        
        if (account.getBalance() >= amount) {
            account.setBalance(account.getBalance() - amount);
            System.out.println("Withdrawal successful. New balance: $" + account.getBalance());
        } else {
            System.out.println("Insufficient funds");
        }
    }

    public void transfer(int fromAccountId, int toAccountId, double amount) {
        BankAccount fromAccount = findAccount(fromAccountId);
        BankAccount toAccount = findAccount(toAccountId);
        
        if (fromAccount == null || toAccount == null) {
            System.out.println("One or both accounts not found");
            return;
        }
        
        if (fromAccount.getBalance() >= amount) {
            fromAccount.setBalance(fromAccount.getBalance() - amount);
            toAccount.setBalance(toAccount.getBalance() + amount);
            System.out.println("Transfer successful");
        } else {
            System.out.println("Insufficient funds for transfer");
        }
    }

    private BankAccount findAccount(int accountId) {
        for (BankAccount account : accounts) {
            if (account.getAccountId() == accountId) {
                return account;
            }
        }
        return null;
    }
}

class BankAccount {
    private int accountId;
    private String accountType;
    private double balance;
    private String customerName;

    public BankAccount(int accountId, String accountType, double balance, String customerName) {
        this.accountId = accountId;
        this.accountType = accountType;
        this.balance = balance;
        this.customerName = customerName;
    }

 
    public int getAccountId() { return accountId; }
    public String getAccountType() { return accountType; }
    public double getBalance() { return balance; }
    public String getCustomerName() { return customerName; }
    public void setBalance(double balance) { this.balance = balance; }

    @Override
    public String toString() {
        return String.format("Account[ID=%d, Type=%s, Balance=$%.2f, Customer=%s]",
                accountId, accountType, balance, customerName);
    }
}