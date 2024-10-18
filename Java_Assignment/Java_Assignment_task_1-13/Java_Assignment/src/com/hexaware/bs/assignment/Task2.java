//Task 2: Nested Conditional Statements
//Create a program that simulates an ATM transaction. Display options such as "Check Balance," 
//"Withdraw," "Deposit,". Ask the user to enter their current balance and the amount they want to 
//withdraw or deposit. Implement checks to ensure that the withdrawal amount is not greater than the 
//available balance and that the withdrawal amount is in multiples of 100 or 500. Display appropriate 
//messages for success or failure.


package com.hexaware.bs.assignment;

import java.util.Scanner;

public class Task2 {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
        double balance = 0;

        System.out.println(" ATM Simulator");
        System.out.print("Please enter your initial balance: $");
        balance = scanner.nextDouble();

        while (true) {
            System.out.println("\nATM Menu:");
            System.out.println("1. Check Balance");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Exit");
            System.out.print("Please select an option (1-4): ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1: 
                    System.out.printf("Your current balance is: ", balance);
                    break;

                case 2: 
                    System.out.print("Enter the amount to withdraw: $");
                    double withdrawAmount = scanner.nextDouble();

                    if (withdrawAmount > balance) {
                        System.out.println("Insufficient funds. Withdrawal failed.");
                    } else if (withdrawAmount % 100 != 0 && withdrawAmount % 500 != 0) {
                        System.out.println("Withdrawal amount must be in multiples of 100 or 500. Withdrawal failed.");
                    } else {
                        balance -= withdrawAmount;
                        System.out.printf("Withdrawal successful. Your new balance is: $%.2f\n", balance);
                    }
                    break;

                case 3: 
                    System.out.print("Enter the amount to deposit: $");
                    double depositAmount = scanner.nextDouble();

                    if (depositAmount > 0) {
                        balance += depositAmount;
                        System.out.printf("Deposit successful. Your new balance is: $%.2f\n", balance);
                    } else {
                        System.out.println("Invalid deposit amount. Deposit failed.");
                    }
                    break;

                case 4: 
                    System.out.println("Thank you for using the ATM. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
          }
	    }
}