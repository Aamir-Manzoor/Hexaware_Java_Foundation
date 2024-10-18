package task14.hexaware.bs.service;

import task14.hexaware.bs.entity.Account;
import task14.hexaware.bs.entity.CurrentAccount;
import task14.hexaware.bs.entity.SavingsAccount;
import task14.hexaware.bs.service.ICustomerServiceProvider;
import task14.hexware.bs.execption.InsufficientFundException;
import task14.hexware.bs.execption.InvalidAccountException;
import task14.hexware.bs.execption.OverDraftLimitExceededException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CustomerServiceProviderImpl implements ICustomerServiceProvider {

    // List to hold the accounts (in-memory)
    private List<Account> accounts = new ArrayList<>();

    // Method to retrieve the balance of an account given its account number
    @Override
    public double getAccountBalance(long accountNumber) throws InvalidAccountException {
        Account account = getAccountDetails(accountNumber);
        if (account != null) {
            return account.getBalance();
        }
        throw new InvalidAccountException("Account number " + accountNumber + " does not exist.");
    }

    // Method to deposit the specified amount into the account
    @Override
    public double deposit(long accountNumber, float amount) throws InvalidAccountException {
        Account account = getAccountDetails(accountNumber);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            return account.getBalance();
        }
        throw new InvalidAccountException("Account number " + accountNumber + " does not exist.");
    }

    // Method to withdraw the specified amount from the account
    @Override
    public double withdraw(long accountNumber, float amount) throws Exception {
        Account account = getAccountDetails(accountNumber);
        if (account != null) {
            if (account instanceof SavingsAccount) {
                // Ensure minimum balance rule for SavingsAccount
                if (account.getBalance() - amount < ((SavingsAccount) account).getMinimumBalance()) {
                    throw new InsufficientFundException("Insufficient funds. Minimum balance must be maintained.");
                }
            } else if (account instanceof CurrentAccount) {
                // Check overdraft limit for CurrentAccount
                if (account.getBalance() - amount < -((CurrentAccount) account).getOverdraftLimit()) {
                    throw new OverDraftLimitExceededException("Overdraft limit exceeded.");
                }
            }
            account.setBalance(account.getBalance() - amount);
            return account.getBalance();
        }
        throw new InvalidAccountException("Account number " + accountNumber + " does not exist.");
    }

    // Method to transfer money from one account to another
    @Override
    public void transfer(long fromAccountNumber, long toAccountNumber, float amount) throws Exception {
        Account fromAccount = getAccountDetails(fromAccountNumber);
        Account toAccount = getAccountDetails(toAccountNumber);

        if (fromAccount != null && toAccount != null) {
            withdraw(fromAccountNumber, amount);  // Withdraw from source account
            deposit(toAccountNumber, amount);     // Deposit into destination account
        } else {
            throw new InvalidAccountException("One of the account numbers does not exist.");
        }
    }

    // Method to get the account and customer details
    @Override
    public Account getAccountDetails(long accountNumber) throws InvalidAccountException {
        return accounts.stream()
                .filter(account -> account.getAccountNumber() == accountNumber)
                .findFirst()
                .orElse(null);
    }

    // Method to get the list of transactions between two dates (dummy implementation)
    @Override
    public List<String> getTransactions(long accountNumber, Date fromDate, Date toDate) {
        // Dummy implementation - Replace with actual transaction retrieval
        List<String> transactions = new ArrayList<>();
        transactions.add("Transaction 1");
        transactions.add("Transaction 2");
        return transactions;
    }

    // Method to add accounts to the list (for testing purposes)
    public void addAccount(Account account) {
        accounts.add(account);
    }
}
