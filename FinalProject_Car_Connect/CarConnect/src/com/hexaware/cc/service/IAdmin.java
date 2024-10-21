/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (AdminService)
 * Date: 21/10/2024
 */

package com.hexaware.cc.service;

import com.hexaware.cc.entity.Admin;
import com.hexaware.cc.exception.AdminNotFoundException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.DatabaseConnectionException;

public interface IAdmin {

    Admin getAdminById(int adminId) throws AdminNotFoundException, DatabaseConnectionException;

    Admin getAdminByUsername(String username) throws AdminNotFoundException, DatabaseConnectionException;

    int registerAdmin(Admin adminData) throws InvalidInputException, DatabaseConnectionException;

    int updateAdmin(Admin adminData) throws AdminNotFoundException, InvalidInputException, DatabaseConnectionException;

    int deleteAdmin(int adminId) throws AdminNotFoundException, DatabaseConnectionException;
}