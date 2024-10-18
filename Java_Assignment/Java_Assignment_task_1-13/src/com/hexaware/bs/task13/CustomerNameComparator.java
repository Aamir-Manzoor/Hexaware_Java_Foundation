package com.hexaware.bs.task13;

import java.util.Comparator;

public class CustomerNameComparator implements Comparator<Account> {
    @Override
    public int compare(Account a1, Account a2) {
        return a1.getCustomerName().compareTo(a2.getCustomerName());
    }
}