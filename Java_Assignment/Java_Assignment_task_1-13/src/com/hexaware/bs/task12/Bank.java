package com.hexaware.bs.task12;

import java.util.HashMap;
import java.util.Map;

public class Bank {
    private Map<Integer, Account> accounts;

    public Bank() {
        this.accounts = new HashMap<>();
    }

    public void createAccount(int accountNumber, String accountType, double initialBalance) {
        Account account;
        if (accountType.equalsIgnoreCase("Savings")) {
            account = new SavingsAccount(accountNumber, initialBalance);
        } else {
            account = new CurrentAccount(accountNumber, initialBalance);
        }
        accounts.put(accountNumber, account);
    }

    public void withdraw(int accountNumber, double amount) 
            throws InvalidAccountException, InsufficientFundException, OverDraftLimitExcededException {
        Account account = getAccount(accountNumber);
        account.withdraw(amount);
    }

    public void transfer(int fromAccount, int toAccount, double amount) 
            throws InvalidAccountException, InsufficientFundException, OverDraftLimitExcededException {
        Account source = getAccount(fromAccount);
        Account destination = getAccount(toAccount);
        
     
        source.withdraw(amount);
       
        destination.balance += amount;
    }

    public Account getAccount(int accountNumber) throws InvalidAccountException {
        Account account = accounts.get(accountNumber);
        if (account == null) {
            throw new InvalidAccountException("Account number " + accountNumber + " does not exist");
        }
        return account;
    }

    public void displayBalance(int accountNumber) throws InvalidAccountException {
        Account account = getAccount(accountNumber);
        System.out.println("Account " + accountNumber + " balance: " + account.getBalance());
    }
}