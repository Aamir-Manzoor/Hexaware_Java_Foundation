package com.hexaware.bs.task13;

public class SavingsAccount extends Account {
    private static final double MIN_BALANCE = 1000.0;

    public SavingsAccount(int accountNumber, double balance, String customerName) {
        super(accountNumber, balance, "Savings", customerName);
    }

    @Override
    void withdraw(double amount) throws InsufficientFundException {
        if (getBalance() - amount < MIN_BALANCE) {
            throw new InsufficientFundException("Insufficient funds: Minimum balance of " + MIN_BALANCE + " must be maintained");
        }
        balance -= amount;
    }
}