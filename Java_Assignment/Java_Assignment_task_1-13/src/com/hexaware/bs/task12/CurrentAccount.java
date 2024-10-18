package com.hexaware.bs.task12;

public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 10000.0;

    public CurrentAccount(int accountNumber, double balance) {
        super(accountNumber, balance, "Current");
    }

    @Override
    void withdraw(double amount) throws OverDraftLimitExcededException {
        if (Math.abs(balance - amount) > OVERDRAFT_LIMIT) {
            throw new OverDraftLimitExcededException("Overdraft limit of " + OVERDRAFT_LIMIT + " exceeded");
        }
        balance -= amount;
    }
}