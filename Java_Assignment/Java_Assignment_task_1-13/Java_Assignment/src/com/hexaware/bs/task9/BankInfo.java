package com.hexaware.bs.task9;

import java.util.Scanner;

public class BankInfo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankInfo bank = new BankInfo();
        BankAcc account = null;

        while (true) {
            System.out.println("////////////// Welcome to the Banking System //////////////");
            System.out.println("Choose account type to create:");
            System.out.println("1. Savings Account");
            System.out.println("2. Current Account");
            System.out.println("3. Exit");

            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    account = bank.createSavingsAccount(scanner);
                    break;

                case 2:
                    account = bank.createCurrentAccount(scanner);
                    break;

                case 3:
                    System.out.println("Exited");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    continue;
            }

            bank.performAccountOperations(scanner, account);
        }
    }

    private SavingsAcc createSavingsAccount(Scanner scanner) {
        System.out.println("Creating a Savings Account:");
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter Customer Name: ");
        scanner.nextLine(); // Consume the newline
        String customerName = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();
        System.out.print("Enter Interest Rate (e.g., 0.03 for 3%): ");
        double interestRate = scanner.nextDouble();

        return new SavingsAcc(accountNumber, customerName, balance, interestRate);
    }

    private CurrentAcc createCurrentAccount(Scanner scanner) {
        System.out.println("Creating a Current Account:");
        System.out.print("Enter Account Number: ");
        int accountNumber = scanner.nextInt();
        System.out.print("Enter Customer Name: ");
        scanner.nextLine(); // Consume the newline
        String customerName = scanner.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = scanner.nextDouble();

        return new CurrentAcc(accountNumber, customerName, balance);
    }

    private void performAccountOperations(Scanner scanner, BankAcc account) {
        while (true) {
            System.out.println("Select operation:");
            System.out.println("1. Deposit");
            System.out.println("2. Withdraw");
            if (account instanceof SavingsAcc) {
                System.out.println("3. Calculate Interest");
            }
            System.out.println("4. Exit");

            int operationChoice = scanner.nextInt();
            switch (operationChoice) {
                case 1:
                    System.out.print("Enter amount to deposit: ");
                    float depositAmount = scanner.nextFloat();
                    account.deposit(depositAmount);
                    break;

                case 2:
                    System.out.print("Enter amount to withdraw: ");
                    float withdrawAmount = scanner.nextFloat();
                    account.withdraw(withdrawAmount);
                    break;

                case 3:
                    if (account instanceof SavingsAcc) {
                        account.calculateInterest();
                    } else {
                        System.out.println("Interest calculation is not applicable for Current Accounts.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting operations for this account.");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
            account.printAccountDetails();
        }
    }
}