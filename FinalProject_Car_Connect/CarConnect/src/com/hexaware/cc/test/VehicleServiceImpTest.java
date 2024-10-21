/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (VehicleServiceImp Test)
 * Date: 21/10/2024
 */


package com.hexaware.cc.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.hexaware.cc.entity.Vehicle;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.VehicleNotFoundException;
import com.hexaware.cc.service.IVehicle;
import com.hexaware.cc.service.VehicleServiceImp;
import java.util.List;

class VehicleServiceImpTest {
    
    static IVehicle service;
    
    @BeforeAll
    public static void beforeAll() throws DatabaseConnectionException {
        service = new VehicleServiceImp();
    }
    
    @Test
    void testVehicleServiceImp() {
        assertNotNull(service, "Service should not be null");
    }
    
    @Test
    void testGetVehicleById() throws VehicleNotFoundException, DatabaseConnectionException {
        int vehicleId = 1;
        Vehicle vehicle = service.getVehicleById(vehicleId);
        
        assertNotNull(vehicle, "Vehicle should not be null");
        assertEquals(vehicleId, vehicle.getVehicleID(), "Vehicle ID should match");
    }
    
    @Test
    void testGetAvailableVehicles() throws DatabaseConnectionException {
        List<Vehicle> vehicles = service.getAvailableVehicles();
        
        assertNotNull(vehicles, "Vehicles list should not be null");
        assertFalse(vehicles.isEmpty(), "Available vehicles list should not be empty");
    }
    
    @Test
    void testAddVehicle() throws InvalidInputException, DatabaseConnectionException {
        Vehicle vehicle = new Vehicle();
        // Set necessary vehicle details
        
        int result = service.addVehicle(vehicle);
        assertTrue(result > 0, "Vehicle should be added successfully");
    }
    
    @Test
    void testUpdateVehicle() throws VehicleNotFoundException, InvalidInputException, DatabaseConnectionException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVehicleID(1);
        // Update necessary fields
        
        int result = service.updateVehicle(vehicle);
        assertEquals(1, result, "Vehicle should be updated successfully");
    }
    
    @Test
    void testRemoveVehicle() throws VehicleNotFoundException, DatabaseConnectionException {
        int vehicleId = 1;
        
        int result = service.removeVehicle(vehicleId);
        assertEquals(1, result, "Vehicle should be removed successfully");
    }
}