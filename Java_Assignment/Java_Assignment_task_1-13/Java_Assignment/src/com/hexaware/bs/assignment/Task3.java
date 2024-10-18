//Task 3: Loop Structures
//You are responsible for calculating compound interest on savings accounts for bank customers. You 
//need to calculate the future balance for each customer's savings account after a certain number of years.
//Tasks:
//1. Create a program that calculates the future balance of a savings account.
//2. Use a loop structure (e.g., for loop) to calculate the balance for multiple customers.
//3. Prompt the user to enter the initial balance, annual interest rate, and the number of years.
//4. Calculate the future balance using the formula: 
//future_balance = initial_balance * (1 + annual_interest_rate/100)^years.
//5. Display the future balance for each customer.


package com.hexaware.bs.assignment;

import java.util.Scanner;

public class Task3{
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

        System.out.print("Enter the number of customers: ");
        int numberOfCustomers = scanner.nextInt();

        for (int i = 1; i <= numberOfCustomers; i++) {
            System.out.println("\nCustomer " + i + ":");
            
      
            System.out.print("Enter initial balance: $");
            double initialBalance = scanner.nextDouble();

           
            System.out.print("Enter annual interest rate (%): ");
            double annualInterestRate = scanner.nextDouble();

            
            System.out.print("Enter number of years: ");
            int years = scanner.nextInt();

         
            double futureBalance = calculateFutureBalance(initialBalance, annualInterestRate, years);

           
            System.out.printf("Future balance after %d years: $%.2f\n", years, futureBalance);
        }

        scanner.close();
    }

    private static double calculateFutureBalance(double initialBalance, double annualInterestRate, int years) {
      
        double rateDecimal = annualInterestRate / 100;

        return initialBalance * Math.pow(1 + rateDecimal, years);
	}
}