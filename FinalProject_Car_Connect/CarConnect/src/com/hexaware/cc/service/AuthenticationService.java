/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (AuthenticationService)
 * Date: 21/10/2024
 */


package com.hexaware.cc.service;

import com.hexaware.cc.entity.Admin;
import com.hexaware.cc.entity.Customer;
import com.hexaware.cc.exception.AdminNotFoundException;
import com.hexaware.cc.exception.AuthenticationException;
import com.hexaware.cc.exception.CustomerNotFoundException;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;

public class AuthenticationService {
    private final IAdmin adminService;
    private final ICustomer customerService;

    public AuthenticationService(IAdmin adminService, ICustomer customerService) {
        this.adminService = adminService;
        this.customerService = customerService;
    }

    public boolean authenticateAdmin(String username, String password) throws AuthenticationException, DatabaseConnectionException {
        try {
            Admin admin = adminService.getAdminByUsername(username);
            if (admin == null || !admin.authenticate(password)) {
                throw new AuthenticationException("Invalid admin credentials");
            }
            return true;
        } catch (AdminNotFoundException e) {
            throw new AuthenticationException("Invalid admin credentials");
        }
    }

    public boolean authenticateCustomer(String username, String password) throws AuthenticationException, DatabaseConnectionException, CustomerNotFoundException {
        Customer customer = customerService.getCustomerByUsername(username);
		if (customer == null || !customer.authenticate(password)) {
		    throw new AuthenticationException("Invalid customer credentials");
		}
		return true;
    }

    public String getUserRole(String username) throws DatabaseConnectionException, CustomerNotFoundException {
        try {
            Admin admin = adminService.getAdminByUsername(username);
            if (admin != null) {
                return "ADMIN";
            }
        } catch (AdminNotFoundException ignored) {}

        Customer customer = customerService.getCustomerByUsername(username);
		if (customer != null) {
		    return "CUSTOMER";
		}

        return null;
    }

    public boolean changePassword(String username, String oldPassword, String newPassword) 
            throws AuthenticationException, InvalidInputException, DatabaseConnectionException, CustomerNotFoundException {
        try {
            Admin admin = adminService.getAdminByUsername(username);
            if (admin != null && admin.authenticate(oldPassword)) {
                admin.setPassword(newPassword);
                adminService.updateAdmin(admin);
                return true;
            }
        } catch (AdminNotFoundException ignored) {}

        Customer customer = customerService.getCustomerByUsername(username);
		if (customer != null && customer.authenticate(oldPassword)) {
		    customer.setPassword(newPassword);
		    customerService.updateCustomer(customer);
		    return true;
		}

        throw new AuthenticationException("Invalid credentials or user not found");
    }
}