package com.hexaware.bs.task7;
public class Account {
    private String accountNumber;
    private String accountType;
    private double accountBalance;


    public Account() {
    	super();
    }

 
    public Account(String accountNumber, String accountType, double accountBalance) {
        
    	super();
    	
    	this.accountNumber = accountNumber;
        this.accountType = accountType;
        this.accountBalance = accountBalance;
    }


    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }

    public String getAccountType() { return accountType; }
    public void setAccountType(String accountType) { this.accountType = accountType; }

    public double getAccountBalance() { return accountBalance; }
    public void setAccountBalance(double accountBalance) { this.accountBalance = accountBalance; }

  
    public void printInfo() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Account Type: " + accountType);
        System.out.println("Balance: $" + accountBalance);
    }


    public void deposit(float amount) {
        accountBalance += amount;
        System.out.println("Deposited: $" + amount);
        System.out.println("New Balance: $" + accountBalance);
    }


    public void withdraw(float amount) {
        if (amount <= accountBalance) {
            accountBalance -= amount;
            System.out.println("Withdrawn: $" + amount);
            System.out.println("New Balance: $" + accountBalance);
        } else {
            System.out.println("Insufficient balance");
        }
    }

  
    public void calculateInterest() {
        double interestRate = 0.045; 
        double interest = accountBalance * interestRate;
        accountBalance += interest;
        System.out.println("Interest added: $" + interest);
        System.out.println("New Balance: $" + accountBalance);
    }
}