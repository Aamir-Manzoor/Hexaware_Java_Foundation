
/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (CustomerServiceImp Test)
 * Date: 21/10/2024
 */

package com.hexaware.cc.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.hexaware.cc.entity.Customer;
import com.hexaware.cc.exception.CustomerNotFoundException;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.service.CustomerServiceImp;
import com.hexaware.cc.service.ICustomer;

class CustomerServiceImpTest {
    
    static ICustomer service;
    
    @BeforeAll
    public static void beforeAll() throws DatabaseConnectionException {
        service = new CustomerServiceImp();
    }
    
    @Test
    void testCustomerServiceImp() {
        assertNotNull(service, "Service should not be null");
    }
    
    @Test
    void testGetCustomerById() throws CustomerNotFoundException, DatabaseConnectionException {
        int customerId = 7;
        Customer customer = service.getCustomerById(customerId);
        
        assertNotNull(customer, "Customer should not be null");
        assertEquals(customerId, customer.getCustomerID(), "Customer ID should match");
    }
    
    @Test
    void testGetCustomerByUsername() throws CustomerNotFoundException, DatabaseConnectionException {
        String username = "amit_s";
        Customer customer = service.getCustomerByUsername(username);
        
        assertNotNull(customer, "Customer should not be null");
        assertEquals(username, customer.getUsername(), "Username should match");
    }
    
    @Test
    void testRegisterCustomer() throws DatabaseConnectionException, InvalidInputException {
        Customer customer = new Customer();
        customer.setUsername("newCustomer");
        customer.setPassword("password123");
        
        int result = service.registerCustomer(customer);
        assertTrue(result > 0, "Customer should be registered successfully");
    }
    
    @Test
    void testUpdateCustomer() throws CustomerNotFoundException, DatabaseConnectionException, InvalidInputException {
        Customer customer = new Customer();
        customer.setCustomerID(7);
        customer.setUsername("updatedCustomer");
        
        int result = service.updateCustomer(customer);
        assertEquals(1, result, "Customer should be updated successfully");
    }
    
    @Test
    void testDeleteCustomer() throws CustomerNotFoundException, DatabaseConnectionException {
        int customerId = 7;
        
        int result = service.deleteCustomer(customerId);
        assertEquals(1, result, "Customer should be deleted successfully");
    }
}