package com.hexaware.bs.task13;

import java.util.HashMap;
import java.util.Map;

public class HMBankMap {
    private Map<Integer, Account> accounts;

    public HMBankMap() {
        this.accounts = new HashMap<>();
    }

    public void createAccount(int accountNumber, String accountType, double initialBalance, String customerName) {
        Account account;
        if (accountType.equalsIgnoreCase("Savings")) {
            account = new SavingsAccount(accountNumber, initialBalance, customerName);
        } else {
            account = new CurrentAccount(accountNumber, initialBalance, customerName);
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

    public void listAccounts() {
        accounts.values().stream()
               .sorted(new CustomerNameComparator())
               .forEach(System.out::println);
    }
}