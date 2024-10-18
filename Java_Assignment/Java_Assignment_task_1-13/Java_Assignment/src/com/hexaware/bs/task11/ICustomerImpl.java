package com.hexaware.bs.task11;



import java.util.HashMap;
import java.util.Map;

public class ICustomerImpl implements ICustomer {
    protected Map<Long, Account> accounts = new HashMap<>();

    @Override
    public float getAccountBalance(long accountNumber) {
        Account account = accounts.get(accountNumber);
        return (account != null) ? account.getBalance() : -1;
    }

    @Override
    public float deposit(long accountNumber, float amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            account.setBalance(account.getBalance() + amount);
            return account.getBalance();
        }
        return -1;
    }

    @Override
    public float withdraw(long accountNumber, float amount) {
        Account account = accounts.get(accountNumber);
        if (account != null) {
            if (account instanceof SavingsAccount) {
                if (account.getBalance() - amount >= SavingsAccount.MINIMUM_BALANCE) {
                    account.setBalance(account.getBalance() - amount);
                    return account.getBalance();
                }
            } else if (account instanceof CurrentAccount) {
                CurrentAccount currentAccount = (CurrentAccount) account;
                if (currentAccount.withdraw(amount)) {
                    return currentAccount.getBalance();
                }
            } else {
                if (account.getBalance() >= amount) {
                    account.setBalance(account.getBalance() - amount);
                    return account.getBalance();
                }
            }
        }
        return -1;
    }

    @Override
    public boolean transfer(long fromAccountNumber, long toAccountNumber, float amount) {
        if (withdraw(fromAccountNumber, amount) != -1) {
            if (deposit(toAccountNumber, amount) != -1) {
                return true;
            } else {
                deposit(fromAccountNumber, amount); 
            }
        }
        return false;
    }

    @Override
    public String getAccountDetails(long accountNumber) {
        Account account = accounts.get(accountNumber);
        return (account != null) ? account.toString() : "Account not found";
    }
}