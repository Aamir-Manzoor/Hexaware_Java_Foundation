//Task 5: Password Validation
//Write a program that prompts the user to create a password for their bank account. Implement if 
//conditions to validate the password according to these rules:
//• The password must be at least 8 characters long.
//• It must contain at least one uppercase letter.
//• It must contain at least one digit.
//• Display appropriate messages to indicate whether their password is valid or not.



package com.hexaware.bs.assignment;

import java.util.Scanner;

public class Task5{
	public static void main(String[] args) {
		 Scanner scanner = new Scanner(System.in);

	        while (true) {
	            System.out.println("Create a password for your bank account.");
	            System.out.println("The password must:");
	            System.out.println("- Be at least 8 characters long");
	            System.out.println("- Contain at least one uppercase letter");
	            System.out.println("- Contain at least one digit");
	            System.out.print("Enter your password: ");

	            String password = scanner.nextLine();

	            if (isValidPassword(password)) {
	                System.out.println("Password is valid. Your account is secure!");
	                break;
	            } else {
	                System.out.println("Password is invalid. Please try again.\n");
	            }
	        }

	        scanner.close();
	    }

	    private static boolean isValidPassword(String password) {
	        boolean isValid = true;
	        String message = "";

	        if (password.length() < 8) {
	            isValid = false;
	            message += "Password must be at least 8 characters long.\n";
	        }

	        if (!password.matches(".*[A-Z].*")) {
	            isValid = false;
	            message += "Password must contain at least one uppercase letter.\n";
	        }

	        // Check for digit
	        if (!password.matches(".*\\d.*")) {
	            isValid = false;
	            message += "Password must contain at least one digit.\n";
	        }

	        if (!isValid) {
	            System.out.println(message);
	        }

	        return isValid;
	}
}