/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (CustomerService)
 * Date: 21/10/2024
 */


package com.hexaware.cc.service;

import com.hexaware.cc.entity.Customer;
import com.hexaware.cc.exception.CustomerNotFoundException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.DatabaseConnectionException;

public interface ICustomer {

    Customer getCustomerById(int customerId) throws CustomerNotFoundException, DatabaseConnectionException;

    Customer getCustomerByUsername(String username) throws CustomerNotFoundException, DatabaseConnectionException;

    int registerCustomer(Customer customerData) throws InvalidInputException, DatabaseConnectionException;

    int updateCustomer(Customer customerData) throws CustomerNotFoundException, InvalidInputException, DatabaseConnectionException;

    int deleteCustomer(int customerId) throws CustomerNotFoundException, DatabaseConnectionException;
}