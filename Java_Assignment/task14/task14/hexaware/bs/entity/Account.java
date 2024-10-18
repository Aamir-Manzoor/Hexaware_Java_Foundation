package task14.hexaware.bs.entity;

public abstract class Account {
    private static long lastAccNo = 1000000000L;  // Initial account number
    private long accountNumber;
    private String accountType;
    private double balance;
    private Customer customer;

    // Constructor
    public Account(long accountNumber, String accountType, double balance, Customer customer) {
        this.accountNumber = ++lastAccNo;  // Auto-generate account number
        this.accountType = accountType;
        this.balance = balance;
        this.customer = customer;
    }

    // Getters and Setters
    public long getAccountNumber() {
        return accountNumber;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountNumber=" + accountNumber +
                ", accountType='" + accountType + '\'' +
                ", balance=" + balance +
                ", customer=" + customer +
                '}';
    }
}

