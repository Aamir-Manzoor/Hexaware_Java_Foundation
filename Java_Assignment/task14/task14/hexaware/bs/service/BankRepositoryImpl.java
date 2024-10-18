package task14.hexaware.bs.service;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import task14.hexaware.bs.entity.Account;
import task14.hexaware.bs.entity.CurrentAccount;
import task14.hexaware.bs.entity.Customer;
import task14.hexaware.bs.entity.SavingsAccount;
import task14.hexaware.bs.entity.Transaction;
import task14.hexaware.bs.entity.ZeroBalanceAccount;
import task14.hexaware.bs.util.DBUtil;
import task14.hexware.bs.execption.InvalidAccountException;

public class BankRepositoryImpl implements IBankRepository {

    private Connection connection;
    
    

 
    public BankRepositoryImpl() {
        connection = DBUtil.getDBConn();
    }

    @Override
    public void createAccount(Account account) {
        String sql = "INSERT INTO Accounts (account_number, account_type, balance, customer_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, account.getAccountNumber());
            pstmt.setString(2, account.getAccountType());
            pstmt.setDouble(3, account.getBalance());
            pstmt.setLong(4, account.getCustomer().getCustomerId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); 
        }
    }

    @Override
    public List<Account> listAccounts() {
        List<Account> accountsList = new ArrayList<>();
        String sql = "SELECT * FROM Accounts";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                long accountNumber = rs.getLong("account_number");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                long customerId = rs.getLong("customer_id");
                Customer customer = getCustomerById(customerId); // Implement this method to fetch customer details
                Account account = createAccountFromResultSet(accountNumber, accountType, balance, customer);
                accountsList.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return accountsList;
    }

    private Account createAccountFromResultSet(long accountNumber, String accountType, double balance, Customer customer) {
        switch (accountType.toLowerCase()) {
            case "savings":
                return new SavingsAccount(accountNumber, customer, balance, SavingsAccount.getMinimumBalance());
            case "current":
                return new CurrentAccount(accountNumber, customer, balance, 1000); // Example overdraft limit
            case "zerobalance":
                return new ZeroBalanceAccount(accountNumber, customer);
            default:
                throw new IllegalArgumentException("Invalid account type.");
        }
    }

    @Override
    public double getBalance(long accountNumber) {
        String sql = "SELECT balance FROM Accounts WHERE account_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return 0.0;
    }

    @Override
    public void deposit(long accountNumber, double amount) {
        String sql = "UPDATE Accounts SET balance = balance + ? WHERE account_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setLong(2, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @Override
    public void withdraw(long accountNumber, double amount) {
        String sql = "UPDATE Accounts SET balance = balance - ? WHERE account_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, amount);
            pstmt.setLong(2, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @Override
    public void transfer(long fromAccountNumber, long toAccountNumber, double amount) {
        try {
            connection.setAutoCommit(false); // Begin transaction

            withdraw(fromAccountNumber, amount);
            deposit(toAccountNumber, amount);

            connection.commit(); // Commit transaction
        } catch (SQLException e) {
            try {
                connection.rollback(); // Rollback transaction on error
            } catch (SQLException ex) {
                ex.printStackTrace(); // Handle rollback error
            }
            e.printStackTrace(); // Handle other exceptions
        } finally {
            try {
                connection.setAutoCommit(true); // Reset to default
            } catch (SQLException e) {
                e.printStackTrace(); // Handle exception
            }
        }
    }

    @Override
    public Account getAccountDetails(long accountNumber) throws InvalidAccountException {
        String sql = "SELECT * FROM Accounts WHERE account_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, accountNumber);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                long customerId = rs.getLong("customer_id");
                String accountType = rs.getString("account_type");
                double balance = rs.getDouble("balance");
                Customer customer = getCustomerById(customerId); // Implement this method to fetch customer details
                return createAccountFromResultSet(accountNumber, accountType, balance, customer);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        throw new InvalidAccountException("Account number " + accountNumber + " does not exist.");
    }

    @Override
    public List<Transaction> getTransactions(long accountNumber, Date fromDate, Date toDate) throws InvalidAccountException {
        List<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT * FROM Transactions WHERE account_id = ? AND transaction_date BETWEEN ? AND ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, accountNumber);
            pstmt.setDate(2, new java.sql.Date(fromDate.getTime()));
            pstmt.setDate(3, new java.sql.Date(toDate.getTime()));
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                // Retrieve data from the ResultSet
                String transactionType = rs.getString("transaction_type");
                double amount = rs.getDouble("transaction_amount");
                Date transactionDate = rs.getDate("transaction_date");
                String description = rs.getString("description"); // Ensure this column exists in your database

                // Create a new Transaction object and add it to the list
                Account account = getAccountDetails(accountNumber); // Get the account details for the transaction
                Transaction transaction = new Transaction(account, description, transactionDate, transactionType, amount);
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return transactions;
    }
    
    @Override
    public void saveAccount(Account account) {
        String sql = "INSERT INTO Accounts (account_id, customer_id, account_type, balance) VALUES (?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, account.getAccountNumber());
            pstmt.setLong(2, account.getCustomer().getCustomerId());
            pstmt.setString(3, account.getAccountType());
            pstmt.setDouble(4, account.getBalance());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }

    @Override
    public void updateAccountBalance(long accountNumber, double newBalance) {
        String sql = "UPDATE Accounts SET balance = ? WHERE account_number = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setDouble(1, newBalance);
            pstmt.setLong(2, accountNumber);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
    }
    
    @Override
    public void recordTransaction(Transaction transaction) {
        String sql = "INSERT INTO Transactions (account_number, transaction_type, transaction_amount, transaction_date, description) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, transaction.getAccount().getAccountNumber()); // Account number
            pstmt.setString(2, transaction.getTransactionType()); // Transaction type (e.g., Deposit, Withdrawal)
            pstmt.setDouble(3, transaction.getTransactionAmount()); // Amount
            pstmt.setDate(4, new java.sql.Date(transaction.getTransactionDate().getTime())); // Transaction date
            pstmt.setString(5, transaction.getDescription()); // Description of the transaction
            pstmt.executeUpdate(); // Execute the query to insert the transaction record
        } catch (SQLException e) {
            e.printStackTrace(); // Handle the SQL exception
        }
    }


 // Add the missing method to fetch customer details based on ID
    private Customer getCustomerById(long customerId) {
        String sql = "SELECT * FROM Customers WHERE customer_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setLong(1, customerId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                // Retrieve customer details from the result set
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                Date dob = rs.getDate("DOB");
                String email = rs.getString("email");
                String phoneNumber = rs.getString("phone_number");
                String address = rs.getString("address");
                
                // Create and return a Customer object using the retrieved details
                return new Customer(customerId, firstName, lastName, dob, email, phoneNumber, address);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exception appropriately
        }
        return null; // Return null if the customer is not found
    }
    
    
}
