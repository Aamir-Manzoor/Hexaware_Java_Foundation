package com.hexaware.bs.task10;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank();
        
        while (true) {
            System.out.println("\nBanking System Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Check Balance");
            System.out.println("5. Transfer");
            System.out.println("6. Get Account Details");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            
            try {
                switch (choice) {
                    case 1:
                        createAccount(scanner, bank);
                        break;
                    case 2:
                        performDeposit(scanner, bank);
                        break;
                    case 3:
                        performWithdrawal(scanner, bank);
                        break;
                    case 4:
                        checkBalance(scanner, bank);
                        break;
                    case 5:
                        performTransfer(scanner, bank);
                        break;
                    case 6:
                        getAccountDetails(scanner, bank);
                        break;
                    case 7:
                        System.out.println("Thank you for using the Banking System!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            } catch (IllegalArgumentException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
    
    private static void createAccount(Scanner scanner, Bank bank) {
        System.out.println("\nAccount Type Menu:");
        System.out.println("1. Savings");
        System.out.println("2. Current");
        System.out.print("Choose account type: ");
        int typeChoice = scanner.nextInt();
        scanner.nextLine(); 
        
        String accountType = (typeChoice == 1) ? "Savings" : "Current";
        
        System.out.println("\nEnter Customer Details:");
        System.out.print("Customer ID: ");
        int customerId = scanner.nextInt();
        scanner.nextLine(); 
        
        System.out.print("First Name: ");
        String firstName = scanner.nextLine();
        
        System.out.print("Last Name: ");
        String lastName = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();
        
        System.out.print("Phone Number (10 digits): ");
        String phone = scanner.nextLine();
        
        System.out.print("Address: ");
        String address = scanner.nextLine();
        
        System.out.print("Initial Deposit Amount: ");
        float initialBalance = scanner.nextFloat();
        
        Customer customer = new Customer(customerId, firstName, lastName, email, phone, address);
        Account account = bank.createAccount(customer, accountType, initialBalance);
        System.out.println("Account created successfully! Account Number: " + account.getAccountNumber());
    }
    
    private static void performDeposit(Scanner scanner, Bank bank) {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        System.out.print("Enter Deposit Amount: ");
        float amount = scanner.nextFloat();
        
        float newBalance = bank.deposit(accountNumber, amount);
        System.out.println("Deposit successful. New balance: $" + newBalance);
    }
    
    private static void performWithdrawal(Scanner scanner, Bank bank) {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        System.out.print("Enter Withdrawal Amount: ");
        float amount = scanner.nextFloat();
        
        float newBalance = bank.withdraw(accountNumber, amount);
        System.out.println("Withdrawal successful. New balance: $" + newBalance);
    }
    
    private static void checkBalance(Scanner scanner, Bank bank) {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        
        float balance = bank.getAccountBalance(accountNumber);
        System.out.println("Current balance: $" + balance);
    }
    
    private static void performTransfer(Scanner scanner, Bank bank) {
        System.out.print("Enter Source Account Number: ");
        long fromAccount = scanner.nextLong();
        System.out.print("Enter Destination Account Number: ");
        long toAccount = scanner.nextLong();
        System.out.print("Enter Transfer Amount: ");
        float amount = scanner.nextFloat();
        
        bank.transfer(fromAccount, toAccount, amount);
        System.out.println("Transfer successful!");
    }
    
    private static void getAccountDetails(Scanner scanner, Bank bank) {
        System.out.print("Enter Account Number: ");
        long accountNumber = scanner.nextLong();
        
        Account account = bank.getAccountDetails(accountNumber);
        account.printAccountInfo();
    }
}