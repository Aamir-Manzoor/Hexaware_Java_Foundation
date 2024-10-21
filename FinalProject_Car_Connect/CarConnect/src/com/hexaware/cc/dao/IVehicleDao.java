/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (Vehicle Dao)
 * Date: 21/10/2024
 */


package com.hexaware.cc.dao;

import java.util.List;
import com.hexaware.cc.entity.Vehicle;

public interface IVehicleDao {
    
    Vehicle getVehicleById(int vehicleId);
    
    List<Vehicle> getAvailableVehicles();
    
    int addVehicle(Vehicle vehicleData);
    
    int updateVehicle(Vehicle vehicleData);
    
    int removeVehicle(int vehicleId);
}