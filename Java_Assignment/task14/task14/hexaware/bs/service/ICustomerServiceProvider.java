package task14.hexaware.bs.service;

import task14.hexaware.bs.entity.Account;
import task14.hexware.bs.execption.InvalidAccountException;

import java.util.Date;
import java.util.List;

public interface ICustomerServiceProvider {
    // Method to retrieve the balance of an account given its account number
    double getAccountBalance(long accountNumber) throws InvalidAccountException;

    // Method to deposit the specified amount into the account
    double deposit(long accountNumber, float amount) throws InvalidAccountException;

    // Method to withdraw the specified amount from the account
    double withdraw(long accountNumber, float amount) throws Exception;

    // Method to transfer money from one account to another
    void transfer(long fromAccountNumber, long toAccountNumber, float amount) throws Exception;

    // Method to get the account and customer details
    Account getAccountDetails(long accountNumber) throws InvalidAccountException;

    // Method to get the list of transactions between two dates
    List<String> getTransactions(long accountNumber, Date fromDate, Date toDate);
}

