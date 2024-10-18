//Task 6: List of Bank Transactions
//Create a program that maintains a list of bank transactions (deposits and withdrawals) for a customer. 
//Use a while loop to allow the user to keep adding transactions until they choose to exit. Display the 
//transaction history upon exit using looping statements

package com.hexaware.bs.assignment;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Task6 {
	static class Transaction {
		String type;
		double amount;

		Transaction(String type, double amount) {
			this.type = type;
			this.amount = amount;
		}

		@Override
		public String toString() {
			return String.format("%s: $%.2f", type, amount);
		}
	}

	public static void main(String[] args) {
		List<Transaction> transactions = new ArrayList<>();
		Scanner scanner = new Scanner(System.in);
		double balance = 0;

		System.out.println("Welcome to the Bank Transaction Manager");

		while (true) {
			System.out.println("\nCurrent Balance: $" + String.format("%.2f", balance));
			System.out.println("1. Add Deposit");
			System.out.println("2. Add Withdrawal");
			System.out.println("3. Exit and View Transaction History");
			System.out.print("Enter your choice (1-3): ");

			int choice = scanner.nextInt();
			scanner.nextLine(); 

			if (choice == 1 || choice == 2) {
				System.out.print("Enter amount: $");
				double amount = scanner.nextDouble();
				scanner.nextLine(); 

				if (choice == 1) {
					transactions.add(new Transaction("Deposit", amount));
					balance += amount;
					System.out.println("Deposit added successfully.");
				} else {
					if (amount > balance) {
						System.out.println("Insufficient funds. Withdrawal canceled.");
					} else {
						transactions.add(new Transaction("Withdrawal", amount));
						balance -= amount;
						System.out.println("Withdrawal added successfully.");
					}
				}
			} else if (choice == 3) {
				break;
			} else {
				System.out.println("Invalid choice. Please try again.");
			}
		}

		System.out.println("\nTransaction History:");
		for (int i = 0; i < transactions.size(); i++) {
			System.out.println((i + 1) + ". " + transactions.get(i));
		}

		System.out.println("\nFinal Balance: $" + String.format("%.2f", balance));
		System.out.println("Thank you");

		scanner.close();

	}
}
