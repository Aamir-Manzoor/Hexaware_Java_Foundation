package com.hexaware.bs.task8;

import java.util.Scanner;

public class BankInfo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Account account = null;

        while (true) {
            System.out.println("\n===== Banking System Menu =====");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Calculate Interest");
            System.out.println("5. Check Balance");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            switch (choice) {
                case 1:
                    System.out.println("\nSelect account type:");
                    System.out.println("1. Savings Account");
                    System.out.println("2. Current Account");
                    int accountType = scanner.nextInt();
                    scanner.nextLine(); 

                    System.out.print("Enter account number: ");
                    String accountNumber = scanner.nextLine();
                    System.out.print("Enter account holder name: ");
                    String accountHolderName = scanner.nextLine();
                    System.out.print("Enter initial balance: ");
                    double initialBalance = scanner.nextDouble();

                    switch (accountType) {
                        case 1:
                            System.out.print("Enter interest rate: ");
                            double interestRate = scanner.nextDouble();
                            account = new SavingsAccount(accountNumber, accountHolderName, initialBalance, interestRate);
                            System.out.println("Savings Account created successfully.");
                            break;
                        case 2:
                            account = new CurrentAccount(accountNumber, accountHolderName, initialBalance);
                            System.out.println("Current Account created successfully.");
                            break;
                        default:
                            System.out.println("Invalid account type. Please try again.");
                            continue;
                    }
                    break;

                case 2:
                    if (account == null) {
                        System.out.println(" create an account first.");
                        break;
                    }
                    System.out.print("Enter amount to deposit: ");
                    float depositAmount = scanner.nextFloat();
                    account.deposit(depositAmount);
                    break;

                case 3:
                    if (account == null) {
                        System.out.println(" create an account first.");
                        break;
                    }
                    System.out.print("Enter amount to withdraw: ");
                    float withdrawAmount = scanner.nextFloat();
                    account.withdraw(withdrawAmount);
                    break;

                case 4:
                    if (account == null) {
                        System.out.println(" create an account first.");
                        break;
                    }
                    account.calculateInterest();
                    break;

                case 5:
                    if (account == null) {
                        System.out.println(" create an account first.");
                        break;
                    }
                    System.out.println("Account Holder: " + account.getAccountHolderName());
                    System.out.println("Account Number: " + account.getAccountNumber());
                    System.out.println("Current balance: " + account.getBalance());
                    if (account instanceof SavingsAccount) {
                        System.out.println("Account Type: Savings Account");
                        System.out.println("Interest Rate: " + ((SavingsAccount) account).getInterestRate() + "%");
                    } else if (account instanceof CurrentAccount) {
                        System.out.println("Account Type: Current Account");
                        System.out.println("Overdraft Limit: " + CurrentAccount.getOverdraftLimit());
                    }
                    break;

                case 6:
                    System.out.println("Thank you");
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. try again.");
            }
        }
    }
}