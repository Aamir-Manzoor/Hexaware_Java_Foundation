package com.hexaware.bs.task13;

public abstract class Account implements Comparable<Account> {
    private int accountNumber;
    protected double balance;
    private String accountType;
    private String customerName;

    public Account(int accountNumber, double balance, String accountType, String customerName) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.customerName = customerName;
    }

    public int getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    public String getAccountType() { return accountType; }
    public String getCustomerName() { return customerName; }

    abstract void withdraw(double amount) throws InsufficientFundException, OverDraftLimitExcededException;

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Account account = (Account) obj;
        return accountNumber == account.accountNumber;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(accountNumber);
    }

    @Override
    public int compareTo(Account other) {
        return this.customerName.compareTo(other.customerName);
    }

    @Override
    public String toString() {
        return String.format("Account[number=%d, customer=%s, type=%s, balance=%.2f]", 
            accountNumber, customerName, accountType, balance);
    }
}