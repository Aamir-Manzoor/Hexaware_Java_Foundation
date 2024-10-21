/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (Customer Dao)
 * Date: 21/10/2024
 */


package com.hexaware.cc.dao;

import com.hexaware.cc.entity.Customer;

public interface ICustomerDao {
    
    Customer getCustomerById(int customerId);
    
    Customer getCustomerByUsername(String username);
    
    int registerCustomer(Customer customerData);
    
    int updateCustomer(Customer customerData);
    
    int deleteCustomer(int customerId);
}