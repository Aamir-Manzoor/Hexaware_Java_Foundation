
package com.hexaware.bs.task7;

public class BankInfo {
    public static void main(String[] args) {
        
        Account account = new Account("123456", "Savings", 1000.0);


        account.deposit(500.0f);
        account.withdraw(200.0f);
        account.calculateInterest();

        
        account.printInfo();
    }
}