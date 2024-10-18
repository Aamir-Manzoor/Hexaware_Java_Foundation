package com.hexaware.bs.task8;

public class CurrentAccount extends Account {
    private static final double OVERDRAFT_LIMIT = 1000;

    public CurrentAccount(String accountNumber, String accountHolderName, double balance) {
        super(accountNumber, accountHolderName, balance);
    }

    @Override
    public void withdraw(float amount) {
        if (getBalance() + OVERDRAFT_LIMIT >= amount) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: " + amount);
            if (getBalance() < 0) {
                System.out.println("Warning: Account is overdrawn. Current balance: " + getBalance());
            }
        } else {
            System.out.println("Withdrawal exceeds overdraft limit. Transaction cancelled.");
        }
    }

    @Override
    public void calculateInterest() {
        System.out.println("Current accounts do not earn interest.");
    }

    public static double getOverdraftLimit() {
        return OVERDRAFT_LIMIT;
    }
}