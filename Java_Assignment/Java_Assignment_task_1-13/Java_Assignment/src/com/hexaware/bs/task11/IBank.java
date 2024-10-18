package com.hexaware.bs.task11;

public interface IBank {
    Account createAccount(Customer customer, String accType, float balance);
    Account[] listAccounts();
    void calculateInterest();
}