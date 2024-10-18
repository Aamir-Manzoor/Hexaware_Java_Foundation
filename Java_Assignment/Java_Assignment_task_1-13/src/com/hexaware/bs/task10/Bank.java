package com.hexaware.bs.task10;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Long, Account> accounts;
    private long nextAccountNumber;
    
    public Bank() {
        this.accounts = new HashMap<>();
        this.nextAccountNumber = 1001;
    }
    
    public Account createAccount(Customer customer, String accType, float balance) {
        long accountNumber = nextAccountNumber++;
        Account account = new Account(accountNumber, accType, balance, customer);
        accounts.put(accountNumber, account);
        return account;
    }
    
    public float getAccountBalance(long accountNumber) {
        Account account = findAccount(accountNumber);
        return account.getBalance();
    }
    
    public float deposit(long accountNumber, float amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        
        Account account = findAccount(accountNumber);
        float newBalance = account.getBalance() + amount;
        account.setBalance(newBalance);
        return newBalance;
    }
    
    public float withdraw(long accountNumber, float amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        
        Account account = findAccount(accountNumber);
        if (account.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds");
        }
        
        float newBalance = account.getBalance() - amount;
        account.setBalance(newBalance);
        return newBalance;
    }
    
    public void transfer(long fromAccountNumber, long toAccountNumber, float amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Transfer amount must be positive");
        }
        
        Account fromAccount = findAccount(fromAccountNumber);
        Account toAccount = findAccount(toAccountNumber);
        
        if (fromAccount.getBalance() < amount) {
            throw new IllegalArgumentException("Insufficient funds for transfer");
        }
        
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
    }
    
    public Account getAccountDetails(long accountNumber) {
        return findAccount(accountNumber);
    }
    
    private Account findAccount(long accountNumber) {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
        return account;
    }
}