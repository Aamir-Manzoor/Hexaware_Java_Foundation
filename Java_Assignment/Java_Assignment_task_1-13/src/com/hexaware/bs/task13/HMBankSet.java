package com.hexaware.bs.task13;

import java.util.Set;
import java.util.TreeSet;

public class HMBankSet {
    private Set<Account> accounts;

    public HMBankSet() {
        this.accounts = new TreeSet<>(new CustomerNameComparator());
    }

    public void createAccount(int accountNumber, String accountType, double initialBalance, String customerName) {
        Account account;
        if (accountType.equalsIgnoreCase("Savings")) {
            account = new SavingsAccount(accountNumber, initialBalance, customerName);
        } else {
            account = new CurrentAccount(accountNumber, initialBalance, customerName);
        }
        if (!accounts.add(account)) {
            System.out.println("Account already exists!");
        }
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
        return accounts.stream()
                      .filter(a -> a.getAccountNumber() == accountNumber)
                      .findFirst()
                      .orElseThrow(() -> new InvalidAccountException("Account number " + accountNumber + " does not exist"));
    }

    public void listAccounts() {
        accounts.forEach(System.out::println);
    }
}