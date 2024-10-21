/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (VehicleService)
 * Date: 21/10/2024
 */

package com.hexaware.cc.service;

import java.util.List;
import com.hexaware.cc.entity.Vehicle;
import com.hexaware.cc.exception.VehicleNotFoundException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.DatabaseConnectionException;

public interface IVehicle {
    
    Vehicle getVehicleById(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException;
    
    List<Vehicle> getAvailableVehicles() throws DatabaseConnectionException;
    
    int addVehicle(Vehicle vehicleData) throws InvalidInputException, DatabaseConnectionException;
    
    int updateVehicle(Vehicle vehicleData) throws VehicleNotFoundException, InvalidInputException, DatabaseConnectionException;
    
    int removeVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException;
}