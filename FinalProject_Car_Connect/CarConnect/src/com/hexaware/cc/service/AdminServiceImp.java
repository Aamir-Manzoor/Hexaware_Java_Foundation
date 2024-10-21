/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (AdminServiceImp)
 * Date: 21/10/2024
 */


package com.hexaware.cc.service;

import com.hexaware.cc.dao.AdminDaoImp;
import com.hexaware.cc.dao.IAdminDao;
import com.hexaware.cc.entity.Admin;
import com.hexaware.cc.exception.AdminNotFoundException;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;

public class AdminServiceImp implements IAdmin {
    private IAdminDao adminDao;
    
    public AdminServiceImp() throws DatabaseConnectionException {
        this.adminDao = new AdminDaoImp();
    }
    
    @Override
    public Admin getAdminById(int adminId) throws AdminNotFoundException, DatabaseConnectionException {
        Admin admin = adminDao.getAdminById(adminId);
        if (admin == null) {
            throw new AdminNotFoundException("Admin with ID " + adminId + " not found");
        }
        return admin;
    }

    @Override
    public Admin getAdminByUsername(String username) throws AdminNotFoundException, DatabaseConnectionException {
        Admin admin = adminDao.getAdminByUsername(username);
        if (admin == null) {
            throw new AdminNotFoundException("Admin with username " + username + " not found");
        }
        return admin;
    }

    @Override
    public int registerAdmin(Admin adminData) throws InvalidInputException, DatabaseConnectionException {
        if (adminData == null || !isValidAdminData(adminData)) {
            throw new InvalidInputException("Invalid admin data provided");
        }
        return adminDao.registerAdmin(adminData);
    }

    @Override
    public int updateAdmin(Admin adminData) throws AdminNotFoundException, InvalidInputException, DatabaseConnectionException {
        if (adminData == null || !isValidAdminData(adminData)) {
            throw new InvalidInputException("Invalid admin data provided");
        }
        int result = adminDao.updateAdmin(adminData);
        if (result == 0) {
            throw new AdminNotFoundException("Admin with ID " + adminData.getAdminID() + " not found");
        }
        return result;
    }

    @Override
    public int deleteAdmin(int adminId) throws AdminNotFoundException, DatabaseConnectionException {
        int result = adminDao.deleteAdmin(adminId);
        if (result == 0) {
            throw new AdminNotFoundException("Admin with ID " + adminId + " not found");
        }
        return result;
    }

    private boolean isValidAdminData(Admin admin) {
        return admin.getUsername() != null && !admin.getUsername().isEmpty() &&
               admin.getPassword() != null && !admin.getPassword().isEmpty() &&
               admin.getEmail() != null && !admin.getEmail().isEmpty() &&
               admin.getFirstName() != null && !admin.getFirstName().isEmpty() &&
               admin.getLastName() != null && !admin.getLastName().isEmpty();
    }
}