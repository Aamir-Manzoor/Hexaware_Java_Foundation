
/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (AdminServiceImp Test)
 * Date: 21/10/2024
 */

package com.hexaware.cc.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.hexaware.cc.entity.Admin;
import com.hexaware.cc.exception.AdminNotFoundException;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.service.AdminServiceImp;
import com.hexaware.cc.service.IAdmin;

class AdminServiceImpTest {
    
    static IAdmin service;
    
    @BeforeAll
    public static void beforeAll() throws DatabaseConnectionException {
        service = new AdminServiceImp();
    }
    
    @Test
    void testAdminServiceImp() {
        assertNotNull(service, "Service should not be null");
    }
    
    @Test
    void testGetAdminById() throws AdminNotFoundException, DatabaseConnectionException {
        int adminId = 1;
        
        Admin admin = service.getAdminById(adminId);
        
        assertNotNull(admin, "Admin should not be null");
        assertEquals(adminId, admin.getAdminID(), "Admin ID should match");
    }
    
    @Test
    void testGetAdminByUsername() throws AdminNotFoundException, DatabaseConnectionException {
        String username = "neha_admin";
        
        Admin admin = service.getAdminByUsername(username);
        
        assertNotNull(admin, "Admin should not be null");
        assertEquals(username, admin.getUsername(), "Username should match");
    }
    
    @Test
    void testRegisterAdmin() throws InvalidInputException, DatabaseConnectionException {
        Admin admin = new Admin();
        admin.setUsername("newAdmin");
        
        int result = service.registerAdmin(admin);
        
        assertTrue(result > 0, "Admin should be registered successfully");
    }
    
    @Test
    void testUpdateAdmin() throws AdminNotFoundException, InvalidInputException, DatabaseConnectionException {
        Admin admin = new Admin();
        admin.setAdminID(10);
        admin.setUsername("updatedAdmin");
        
        int result = service.updateAdmin(admin);
        
        assertEquals(1, result, "Admin should be updated successfully");
    }
    
    @Test
    void testDeleteAdmin() throws AdminNotFoundException, DatabaseConnectionException {
        int adminId = 2;
        
        int result = service.deleteAdmin(adminId);
        
        assertEquals(1, result, "Admin should be deleted successfully");
    }
}