/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (CustomerServiceImp)
 * Date: 21/10/2024
 */

package com.hexaware.cc.service;

import com.hexaware.cc.dao.CustomerDaoImp;
import com.hexaware.cc.dao.ICustomerDao;
import com.hexaware.cc.entity.Customer;
import com.hexaware.cc.exception.CustomerNotFoundException;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;

public class CustomerServiceImp implements ICustomer {
    
    private ICustomerDao dao;
    
    public CustomerServiceImp() throws DatabaseConnectionException {
        dao = new CustomerDaoImp();
    }

    @Override
    public Customer getCustomerById(int customerId) throws CustomerNotFoundException, DatabaseConnectionException {
        Customer customer = dao.getCustomerById(customerId);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
        return customer;
    }

    @Override
    public Customer getCustomerByUsername(String username) throws CustomerNotFoundException, DatabaseConnectionException {
        Customer customer = dao.getCustomerByUsername(username);
        if (customer == null) {
            throw new CustomerNotFoundException("Customer with username " + username + " not found");
        }
        return customer;
    }

    @Override
    public int registerCustomer(Customer customerData) throws InvalidInputException, DatabaseConnectionException {
        if (customerData == null || !isValidCustomerData(customerData)) {
            throw new InvalidInputException("Invalid customer data provided");
        }
        return dao.registerCustomer(customerData);
    }

    @Override
    public int updateCustomer(Customer customerData) throws CustomerNotFoundException, InvalidInputException, DatabaseConnectionException {
        if (customerData == null || !isValidCustomerData(customerData)) {
            throw new InvalidInputException("Invalid customer data provided");
        }
        int result = dao.updateCustomer(customerData);
        if (result == 0) {
            throw new CustomerNotFoundException("Customer with ID " + customerData.getCustomerID() + " not found");
        }
        return result;
    }

    @Override
    public int deleteCustomer(int customerId) throws CustomerNotFoundException, DatabaseConnectionException {
        int result = dao.deleteCustomer(customerId);
        if (result == 0) {
            throw new CustomerNotFoundException("Customer with ID " + customerId + " not found");
        }
        return result;
    }

    private boolean isValidCustomerData(Customer customer) {
        return customer.getUsername() != null && !customer.getUsername().isEmpty() &&
               customer.getPassword() != null && !customer.getPassword().isEmpty() &&
               customer.getEmail() != null && !customer.getEmail().isEmpty() &&
               customer.getPhoneNumber() != null && !customer.getPhoneNumber().isEmpty();
    }
}