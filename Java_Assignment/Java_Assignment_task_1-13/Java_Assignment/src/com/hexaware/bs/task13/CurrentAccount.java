package com.hexaware.bs.task13;

public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 10000.0;

    public CurrentAccount(int accountNumber, double balance, String customerName) {
        super(accountNumber, balance, "Current", customerName);
    }

    @Override
    void withdraw(double amount) throws OverDraftLimitExcededException {
        if (Math.abs(getBalance() - amount) > OVERDRAFT_LIMIT) {
            throw new OverDraftLimitExcededException("Overdraft limit of " + OVERDRAFT_LIMIT + " exceeded");
        }
        balance -= amount;
    }
}