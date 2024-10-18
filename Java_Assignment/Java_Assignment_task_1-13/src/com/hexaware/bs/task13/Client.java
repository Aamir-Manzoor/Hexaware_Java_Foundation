package com.hexaware.bs.task13;

public class Client {
    public static void main(String[] args) {
        testListImplementation();
        testSetImplementation();
        testMapImplementation();
    }

    private static void testListImplementation() {
        System.out.println("\n=== Testing ArrayList Implementation ===");
        HMBankList bank = new HMBankList();
        try {
            bank.createAccount(1001, "Savings", 5000, "John Doe");
            bank.createAccount(1002, "Current", 10000, "Alice Smith");
            bank.createAccount(1003, "Savings", 7500, "Bob Johnson");
            
            System.out.println("\nInitial accounts list:");
            bank.listAccounts();

            System.out.println("\nTesting withdrawal:");
            bank.withdraw(1001, 1000);
            bank.displayBalance(1001);

            System.out.println("\nTesting transfer:");
            bank.transfer(1002, 1003, 2000);
            bank.listAccounts();

       
            System.out.println("\nAdding duplicate account:");
            bank.createAccount(1001, "Savings", 6000, "John Doe");
            bank.listAccounts();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void testSetImplementation() {
        System.out.println("\n=== Testing TreeSet Implementation ===");
        HMBankSet bank = new HMBankSet();
        try {
            bank.createAccount(1001, "Savings", 5000, "John Doe");
            bank.createAccount(1002, "Current", 10000, "Alice Smith");
            bank.createAccount(1003, "Savings", 7500, "Bob Johnson");
            
            System.out.println("\nInitial accounts list (automatically sorted):");
            bank.listAccounts();

          
            System.out.println("\nAttempting to add duplicate account:");
            bank.createAccount(1001, "Savings", 6000, "John Doe");
            bank.listAccounts();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void testMapImplementation() {
        System.out.println("\n=== Testing HashMap Implementation ===");
        HMBankMap bank = new HMBankMap();
        try {
            bank.createAccount(1001, "Savings", 5000, "John Doe");
            bank.createAccount(1002, "Current", 10000, "Alice Smith");
            bank.createAccount(1003, "Savings", 7500, "Bob Johnson");
            
            System.out.println("\nInitial accounts list (sorted by customer name):");
            bank.listAccounts();

         
            System.out.println("\nUpdating existing account:");
            bank.createAccount(1001, "Savings", 6000, "John Doe");
            bank.listAccounts();

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}