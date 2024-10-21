/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (CustomerDaoImp)
 * Date: 21/10/2024
 */

package com.hexaware.cc.dao;

import java.sql.Connection;
import java.sql.*;

import com.hexaware.cc.entity.Customer;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.util.DBConnUtil;

public class CustomerDaoImp implements ICustomerDao {
    
    private Connection conn;
    
    public CustomerDaoImp() throws DatabaseConnectionException {
        conn = DBConnUtil.getConnection();
    }

    // Helper methods for validation
    private boolean isValidEmail(String email) {
        return email != null && email.contains("@");
    }

    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("\\d{10}");
    }

    private boolean isValidPassword(String password) {
        return password != null && password.length() >= 8;
    }
    
    @Override
    public Customer getCustomerById(int customerId) {
        try {
            String query = "SELECT * FROM Customer WHERE CustomerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID((int) rs.getLong("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customer.setAddress(rs.getString("Address"));
                customer.setUsername(rs.getString("Username"));
                customer.setPassword(rs.getString("Password"));
                customer.setRegistrationDate(rs.getDate("RegistrationDate"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Customer getCustomerByUsername(String username) {
        try {
            String query = "SELECT * FROM Customer WHERE Username = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setEmail(rs.getString("Email"));
                customer.setPhoneNumber(rs.getString("PhoneNumber"));
                customer.setAddress(rs.getString("Address"));
                customer.setUsername(rs.getString("Username"));
                customer.setPassword(rs.getString("Password"));
                customer.setRegistrationDate(rs.getDate("RegistrationDate"));
                return customer;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int registerCustomer(Customer customerData) {
        // Validate input data
        if (!isValidEmail(customerData.getEmail())) {
            System.out.println("Invalid email format. Email must contain @");
            return 0;
        }
        
        if (!isValidPhoneNumber(customerData.getPhoneNumber())) {
            System.out.println("Invalid phone number. Must be exactly 10 digits");
            return 0;
        }
        
        if (!isValidPassword(customerData.getPassword())) {
            System.out.println("Invalid password. Must be at least 8 characters long");
            return 0;
        }

        int rowsAffected = 0;
        try {
            String query = "INSERT INTO Customer (FirstName, LastName, Email, PhoneNumber, Address, Username, Password, RegistrationDate) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, customerData.getFirstName());
            pstmt.setString(2, customerData.getLastName());
            pstmt.setString(3, customerData.getEmail());
            pstmt.setString(4, customerData.getPhoneNumber());
            pstmt.setString(5, customerData.getAddress());
            pstmt.setString(6, customerData.getUsername());
            pstmt.setString(7, customerData.getPassword());
            pstmt.setDate(8, new java.sql.Date(customerData.getRegistrationDate().getTime()));
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int updateCustomer(Customer customerData) {
        // Validate input data for updates
        if (!isValidEmail(customerData.getEmail())) {
            System.out.println("Invalid email format. Email must contain @");
            return 0;
        }
        
        if (!isValidPhoneNumber(customerData.getPhoneNumber())) {
            System.out.println("Invalid phone number. Must be exactly 10 digits");
            return 0;
        }
        
        if (!isValidPassword(customerData.getPassword())) {
            System.out.println("Invalid password. Must be at least 8 characters long");
            return 0;
        }

        int rowsAffected = 0;
        try {
            String query = "UPDATE Customer SET FirstName = ?, LastName = ?, Email = ?, PhoneNumber = ?, Address = ?, Username = ?, Password = ? WHERE CustomerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, customerData.getFirstName());
            pstmt.setString(2, customerData.getLastName());
            pstmt.setString(3, customerData.getEmail());
            pstmt.setString(4, customerData.getPhoneNumber());
            pstmt.setString(5, customerData.getAddress());
            pstmt.setString(6, customerData.getUsername());
            pstmt.setString(7, customerData.getPassword());
            pstmt.setInt(8, customerData.getCustomerID());
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    @Override
    public int deleteCustomer(int customerId) {
        int rowsAffected = 0;
        try {
            String query = "DELETE FROM Customer WHERE CustomerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}