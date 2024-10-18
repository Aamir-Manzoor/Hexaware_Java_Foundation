package task14.hexaware.bs.entity;

public class SavingsAccount extends Account {
    private static final double MINIMUM_BALANCE = 500.0; // Constant for minimum balance
    private double interestRate;

    public SavingsAccount(long accountNumber, Customer customer, double balance, double interestRate) {
        super(accountNumber, "Savings", balance, customer);
        this.interestRate = interestRate;
        if (balance < 500) {
            throw new IllegalArgumentException("Savings Account should have a minimum balance of 500.");
        }
    }

    // Getters and Setters
    public double getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }
    
 // Method to return the minimum balance
    public static double getMinimumBalance() {
        return MINIMUM_BALANCE;
    }
}
