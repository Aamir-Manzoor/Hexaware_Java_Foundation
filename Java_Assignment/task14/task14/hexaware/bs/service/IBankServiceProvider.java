package task14.hexaware.bs.service;

import task14.hexaware.bs.entity.Account;
import task14.hexaware.bs.entity.Customer;
import task14.hexaware.bs.entity.Transaction;
import task14.hexware.bs.execption.InvalidAccountException;

import java.util.Date;
import java.util.List;


public interface IBankServiceProvider {
	void createAccount(Customer customer, long accNo, String accType, float balance);
	
    void deposit(long accountNumber, double amount) throws InvalidAccountException;
    
    void withdraw(long accountNumber, double amount) throws InvalidAccountException;
    
    double getBalance(long accountNumber) throws InvalidAccountException;
    
    void transfer(long fromAccount, long toAccount, double amount) throws InvalidAccountException;
    
    List<Account> listAccounts();
    
    List<Transaction> getTransactions(long accountId, Date fromDate, Date toDate) throws InvalidAccountException;
    
    Account getAccountDetails(long accountNumber) throws InvalidAccountException;
    
    double calculateInterest(long accountNumber) throws InvalidAccountException;

}


