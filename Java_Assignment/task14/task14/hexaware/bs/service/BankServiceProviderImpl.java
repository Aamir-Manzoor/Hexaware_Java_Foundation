package task14.hexaware.bs.service;

import task14.hexaware.bs.entity.Account;
import task14.hexaware.bs.entity.CurrentAccount;
import task14.hexaware.bs.entity.Customer;
import task14.hexaware.bs.entity.SavingsAccount;
import task14.hexaware.bs.entity.Transaction;
import task14.hexaware.bs.entity.ZeroBalanceAccount;
import task14.hexaware.bs.service.BankRepositoryImpl;
import task14.hexaware.bs.service.IBankRepository;
import task14.hexware.bs.execption.InvalidAccountException;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BankServiceProviderImpl implements IBankServiceProvider {
    
    private List<Account> accountList = new ArrayList<>();
    private IBankRepository bankRepository; // Repository for database operations
    private String branchName;
    private String branchAddress;

    public BankServiceProviderImpl() {
        this.bankRepository = new BankRepositoryImpl(); // Initialize your repository here
    }

    @Override
    public void createAccount(Customer customer, long accNo, String accType, float balance) {
        Account account;
        switch (accType.toLowerCase()) {
            case "savings":
                account = new SavingsAccount(accNo, customer, balance, SavingsAccount.getMinimumBalance());
                break;
            case "current":
                account = new CurrentAccount(accNo, customer, balance, 1000); // Example overdraft limit
                break;
            case "zerobalance":
                account = new ZeroBalanceAccount(accNo, customer);
                break;
            default:
                throw new IllegalArgumentException("Invalid account type.");
        }
        accountList.add(account);
        bankRepository.saveAccount(account); // Save the account to the database
    }

    @Override
    public void deposit(long accountNumber, double amount) throws InvalidAccountException {
        // Get the account from the repository
        Account account = getAccountDetails(accountNumber);
        double currentBalance = account.getBalance();
        double newBalance = currentBalance + amount;
        
        account.setBalance(newBalance); // Update the account balance
        bankRepository.updateAccountBalance(accountNumber, newBalance); // Update in the database
        
        // Optionally, log the transaction
        Transaction transaction = new Transaction(account, "Deposit", new Date(), "Deposit", amount);
        bankRepository.recordTransaction(transaction);
    }

    @Override
    public void withdraw(long accountNumber, double amount) throws InvalidAccountException {
        Account account = getAccountDetails(accountNumber);
        double currentBalance = account.getBalance();
        
        // Check if sufficient funds are available
        if (currentBalance >= amount) {
            double newBalance = currentBalance - amount;
            account.setBalance(newBalance);
            bankRepository.updateAccountBalance(accountNumber, newBalance);
            
            // Optionally, log the transaction
            Transaction transaction = new Transaction(account, "Withdraw", new Date(), "Withdraw", amount);
            bankRepository.recordTransaction(transaction);
        } else {
            throw new IllegalArgumentException("Insufficient funds.");
        }
    }

    @Override
    public double getBalance(long accountNumber) throws InvalidAccountException {
        Account account = getAccountDetails(accountNumber);
        return account.getBalance();
    }

    @Override
    public void transfer(long fromAccount, long toAccount, double amount) throws InvalidAccountException {
        withdraw(fromAccount, amount); // Withdraw from the sender's account
        deposit(toAccount, amount); // Deposit to the receiver's account
        // Optionally, log the transfer transaction
        Transaction transaction = new Transaction(getAccountDetails(fromAccount), "Transfer", new Date(), "Transfer", amount);
        bankRepository.recordTransaction(transaction);
    }

    @Override
    public List<Account> listAccounts() {
        return accountList; // You may want to get this from the repository instead
    }

    @Override
    public Account getAccountDetails(long accountNumber) throws InvalidAccountException {
        // Implement logic to find account in accountList
        for (Account account : accountList) {
            if (account.getAccountNumber() == accountNumber) {
                return account;
            }
        }
        throw new InvalidAccountException("Account number " + accountNumber + " does not exist.");
    }

    @Override
    public List<Transaction> getTransactions(long accountId, Date fromDate, Date toDate) throws InvalidAccountException {
        // Call the repository to get transactions
        return bankRepository.getTransactions(accountId, fromDate, toDate);
    }

    @Override
    public double calculateInterest(long accountNumber) throws InvalidAccountException {
        Account account = getAccountDetails(accountNumber);
        if (account instanceof SavingsAccount) {
            return account.getBalance() * ((SavingsAccount) account).getInterestRate() / 100; // Example interest calculation
        }
        // Add logic for other account types if necessary
        return 0;
    }

    // Getter and Setter for branchName and branchAddress
    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress;
    }
}
