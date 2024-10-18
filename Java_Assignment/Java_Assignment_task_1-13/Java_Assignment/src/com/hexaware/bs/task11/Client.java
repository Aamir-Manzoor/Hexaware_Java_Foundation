package com.hexaware.bs.task11;




import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        IBank bank = new IBankImp("Main Branch", "123 Bank Street");

        while (true) {
            System.out.println("\nBank System Menu:");
            System.out.println("1. Create Account");
            System.out.println("2. Deposit");
            System.out.println("3. Withdraw");
            System.out.println("4. Get Balance");
            System.out.println("5. Transfer");
            System.out.println("6. Get Account Details");
            System.out.println("7. List Accounts");
            System.out.println("8. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    createAccount(scanner, bank);
                    break;
                case 2:
                    performDeposit(scanner, bank);
                    break;
                case 3:
                    performWithdraw(scanner, bank);
                    break;
                case 4:
                    getBalance(scanner, bank);
                    break;
                case 5:
                    performTransfer(scanner, bank);
                    break;
                case 6:
                    getAccountDetails(scanner, bank);
                    break;
                case 7:
                    listAccounts(bank);
                    break;
                case 8:
                    System.out.println("Thank you for using the Bank System. Goodbye!");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void createAccount(Scanner scanner, IBank bank) {
        System.out.println("Enter customer details:");
        System.out.print("Name: ");
        String name = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();
        System.out.print("Phone: ");
        String phone = scanner.nextLine();
        Customer customer = new Customer(String.valueOf(System.currentTimeMillis()), name, email, phone);

        System.out.println("Choose account type:");
        System.out.println("1. Savings");
        System.out.println("2. Current");
        System.out.println("3. Zero Balance");
        int accountChoice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String accountType;
        float initialBalance = 0;

        switch (accountChoice) {
            case 1:
                accountType = "Savings";
                System.out.print("Enter initial balance (minimum 500): ");
                initialBalance = Math.max(500, scanner.nextFloat());
                break;
            case 2:
                accountType = "Current";
                System.out.print("Enter initial balance: ");
                initialBalance = scanner.nextFloat();
                break;
            case 3:
                accountType = "ZeroBalance";
                break;
            default:
                System.out.println("Invalid choice. Creating a Savings account by default.");
                accountType = "Savings";
                initialBalance = 500;
        }

        Account newAccount = bank.createAccount(customer, accountType, initialBalance);
        if (newAccount != null) {
            System.out.println("Account created successfully. Account number: " + newAccount.getAccountNumber());
        } else {
            System.out.println("Failed to create account.");
        }
    }

    private static void performDeposit(Scanner scanner, IBank bank) {
        System.out.print("Enter account number: ");
        long accountNumber = scanner.nextLong();
        System.out.print("Enter deposit amount: ");
        float amount = scanner.nextFloat();

        float newBalance = ((ICustomerImpl)bank).deposit(accountNumber, amount);
        if (newBalance != -1) {
            System.out.println("Deposit successful. New balance: " + newBalance);
        } else {
            System.out.println("Deposit failed. Account not found.");
        }
    }

    private static void performWithdraw(Scanner scanner, IBank bank) {
        System.out.print("Enter account number: ");
        long accountNumber = scanner.nextLong();
        System.out.print("Enter withdrawal amount: ");
        float amount = scanner.nextFloat();

        float newBalance = ((ICustomerImpl)bank).withdraw(accountNumber, amount);
        if (newBalance != -1) {
            System.out.println("Withdrawal successful. New balance: " + newBalance);
        } else {
            System.out.println("Withdrawal failed. Account not found or insufficient funds.");
        }
    }

    private static void getBalance(Scanner scanner, IBank bank) {
        System.out.print("Enter account number: ");
        long accountNumber = scanner.nextLong();

        float balance = ((ICustomerImpl)bank).getAccountBalance(accountNumber);
        if (balance != -1) {
            System.out.println("Account balance: " + balance);
        } else {
            System.out.println("Account not found.");
        }
    }

    private static void performTransfer(Scanner scanner, IBank bank) {
        System.out.print("Enter source account number: ");
        long fromAccountNumber = scanner.nextLong();
        System.out.print("Enter destination account number: ");
        long toAccountNumber = scanner.nextLong();
        System.out.print("Enter transfer amount: ");
        float amount = scanner.nextFloat();

        boolean success = ((ICustomerImpl)bank).transfer(fromAccountNumber, toAccountNumber, amount);
        if (success) {
            System.out.println("Transfer successful.");
        } else {
            System.out.println("Transfer failed. Check account numbers and balance.");
        }
    }

    private static void getAccountDetails(Scanner scanner, IBank bank) {
        System.out.print("Enter account number: ");
        long accountNumber = scanner.nextLong();

        String details = ((ICustomerImpl)bank).getAccountDetails(accountNumber);
        System.out.println(details);
    }

    private static void listAccounts(IBank bank) {
        Account[] accounts = bank.listAccounts();
        if (accounts.length == 0) {
            System.out.println("No accounts found.");
        } else {
            System.out.println("List of all accounts:");
            for (Account account : accounts) {
                System.out.println(account);
            }
        }
    }
}