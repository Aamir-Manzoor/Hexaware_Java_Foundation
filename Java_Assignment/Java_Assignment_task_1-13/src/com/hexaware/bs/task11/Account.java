package com.hexaware.bs.task11;

public abstract class Account {
    private static long lastAccNo = 1000;
    protected long accountNumber;
    protected String accountType;
    protected float balance;
    protected Customer customer;

    public Account(String accountType, float balance, Customer customer) {
        this.accountNumber = ++lastAccNo;
        this.accountType = accountType;
        this.balance = balance;
        this.customer = customer;
    }

    // Getters and setters
    public long getAccountNumber() { return accountNumber; }
    public String getAccountType() { return accountType; }
    public float getBalance() { return balance; }
    public void setBalance(float balance) { this.balance = balance; }
    public Customer getCustomer() { return customer; }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", customer=" + customer +
                '}';
    }
}

class SavingsAccount extends Account {
    private float interestRate;
    static final float MINIMUM_BALANCE = 500;

    public SavingsAccount(float balance, Customer customer, float interestRate) {
        super("Savings", Math.max(balance, MINIMUM_BALANCE), customer);
        this.interestRate = interestRate;
    }

    public float getInterestRate() { return interestRate; }
    public void setInterestRate(float interestRate) { this.interestRate = interestRate; }

    @Override
    public String toString() {
        return super.toString() + ", SavingsAccount{interestRate=" + interestRate + '}';
    }
}

class CurrentAccount extends Account {
    private float overdraftLimit;

    public CurrentAccount(float balance, Customer customer, float overdraftLimit) {
        super("Current", balance, customer);
        this.overdraftLimit = overdraftLimit;
    }

    public float getOverdraftLimit() { return overdraftLimit; }
    public void setOverdraftLimit(float overdraftLimit) { this.overdraftLimit = overdraftLimit; }

    public boolean withdraw(float amount) {
        if (amount <= (balance + overdraftLimit)) {
            balance -= amount;
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return super.toString() + ", CurrentAccount{overdraftLimit=" + overdraftLimit + '}';
    }
}

class ZeroBalanceAccount extends Account {
    public ZeroBalanceAccount(Customer customer) {
        super("Zero Balance", 0, customer);
    }

    @Override
    public String toString() {
        return super.toString() + ", ZeroBalanceAccount{}";
    }
}