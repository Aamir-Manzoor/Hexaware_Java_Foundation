package com.hexaware.bs.task8;

public class SavingsAccount extends Account {
    private double interestRate;

    public SavingsAccount(String accountNumber, String accountHolderName, double balance, double interestRate) {
        super(accountNumber, accountHolderName, balance);
        this.interestRate = interestRate;
    }

    @Override
    public void withdraw(float amount) {
        if (amount > 0 && amount <= getBalance()) {
            setBalance(getBalance() - amount);
            System.out.println("Withdrawn: " + amount);
            System.out.println("New balance: " + getBalance());
        } else {
            System.out.println("Invalid withdrawal amount or insufficient funds.");
        }
    }

    @Override
    public void calculateInterest() {
        double interest = getBalance() * (interestRate / 100);
        setBalance(getBalance() + interest);
        System.out.println("Interest added: " + interest);
        System.out.println("New balance after adding interest: " + getBalance());
    }

    public double getInterestRate() {
        return interestRate;
    }
}