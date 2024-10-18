package com.hexaware.bs.task12;

public class Client {
    public static void main(String[] args) {
        Bank bank = new Bank();
        
        try {
          
            bank.createAccount(1001, "Savings", 5000);
            bank.createAccount(1002, "Current", 10000);

            try {
                System.out.println("Attempting withdrawal from savings account...");
                bank.withdraw(1001, 4500); 
            } catch (InsufficientFundException e) {
                System.out.println("Error: " + e.getMessage());
            }

            try {
                System.out.println("\nAttempting withdrawal from current account...");
                bank.withdraw(1002, 21000);
            } catch (OverDraftLimitExcededException e) {
                System.out.println("Error: " + e.getMessage());
            }

          
            try {
                System.out.println("\nAttempting to access invalid account...");
                bank.displayBalance(1003);
            } catch (InvalidAccountException e) {
                System.out.println("Error: " + e.getMessage());
            }

            // Test transfer
            try {
                System.out.println("\nAttempting transfer between accounts...");
                bank.transfer(1001, 1002, 2000);
                System.out.println("Transfer successful!");
                bank.displayBalance(1001);
                bank.displayBalance(1002);
            } catch (InsufficientFundException | InvalidAccountException | OverDraftLimitExcededException e) {
                System.out.println("Error during transfer: " + e.getMessage());
            }

       
            try {
                System.out.println("\nTesting null pointer handling...");
                Account nullAccount = null;
                nullAccount.getBalance();
            } catch (NullPointerException e) {
                System.out.println("Null pointer exception caught: " + e.getMessage());
            }

        } catch (Exception e) {
            System.out.println("Unexpected error occurred: " + e.getMessage());
        }
    }
}