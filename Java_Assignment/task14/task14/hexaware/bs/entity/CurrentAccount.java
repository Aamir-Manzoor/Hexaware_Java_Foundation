package task14.hexaware.bs.entity;

public class CurrentAccount extends Account {
    private double overdraftLimit;

    public CurrentAccount(long accountNumber, Customer customer, double balance, int overdraftLimit) {
        super(accountNumber, "Current", balance, customer);
        this.overdraftLimit = overdraftLimit;
    }

    // Getters and Setters
    public double getOverdraftLimit() {
        return overdraftLimit;
    }

    public void setOverdraftLimit(double overdraftLimit) {
        this.overdraftLimit = overdraftLimit;
    }
}
