package task14.hexaware.bs.service;

import java.util.Date;
import java.util.List;

import task14.hexaware.bs.entity.Account;
import task14.hexaware.bs.entity.Customer;
import task14.hexaware.bs.entity.Transaction;
import task14.hexware.bs.execption.InvalidAccountException;

public interface IBankRepository {
    
    // Method to create a new bank account
    void createAccount(Account account);

    // Method to list all accounts from the database
    List<Account> listAccounts();

    // Method to retrieve the balance of an account given its account number
    double getBalance(long accountNumber);

    // Method to deposit the specified amount into the account
    void deposit(long accountNumber, double amount);

    // Method to withdraw the specified amount from the account
    void withdraw(long accountNumber, double amount);

    // Method to transfer money from one account to another
    void transfer(long fromAccountNumber, long toAccountNumber, double amount);

    // Method to get account details based on account number
    Account getAccountDetails(long accountNumber) throws InvalidAccountException;
    
    void saveAccount(Account account); // Save a new account to the database
    
    void updateAccountBalance(long accountNumber, double newBalance); // Update the balance for a specific account
    
    void recordTransaction(Transaction transaction); // Record a transaction in the database

    // Method to retrieve transactions between two dates
    List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) throws InvalidAccountException;
}
