//Task 4: Looping, Array and Data Validation
//You are tasked with creating a program that allows bank customers to check their account balances. 
//The program should handle multiple customer accounts, and the customer should be able to enter their 
//account number, balance to check the balance.
//Tasks:
//1. Create a Python program that simulates a bank with multiple customer accounts.
//2. Use a loop (e.g., while loop) to repeatedly ask the user for their account number and 
//balance until they enter a valid account number.
//3. Validate the account number entered by the user.
//4. If the account number is valid, display the account balance. If not, ask the user to try again



package com.hexaware.bs.assignment;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Task4 {
	private static Map<String, Double> accounts = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
      
        accounts.put("1001", 5000.0);
        accounts.put("1002", 3500.0);
        accounts.put("1003", 7200.0);

        while (true) {
            System.out.println("\nBank Account Balance Checker");
            System.out.println("1. Check Balance");
            System.out.println("2. Exit");
            System.out.print("Enter your choice (1-2): ");

            int choice = scanner.nextInt();
            scanner.nextLine(); 

            if (choice == 1) {
                checkBalance();
            } else if (choice == 2) {
                System.out.println("Thank you for using the Bank Account Balance Checker. Goodbye!");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

        scanner.close();
    }

    private static void checkBalance() {
        while (true) {
            System.out.print("Enter your account number: ");
            String accountNumber = scanner.nextLine();

            if (accounts.containsKey(accountNumber)) {
                double balance = accounts.get(accountNumber);
                System.out.printf("Your account balance is: $%.2f\n", balance);
                break;
            } else {
                System.out.println("Invalid account number. Would you like to try again? (yes/no)");
                String retry = scanner.nextLine().toLowerCase();
                if (!retry.equals("yes")) {
                    break;
                }
            }
        }
	}
}

