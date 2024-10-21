/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (VehicleServiceImp)
 * Date: 21/10/2024
 */


package com.hexaware.cc.service;

import java.util.List;
import com.hexaware.cc.dao.IVehicleDao;
import com.hexaware.cc.dao.VehicleDaoImp;
import com.hexaware.cc.entity.Vehicle;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.VehicleNotFoundException;

public class VehicleServiceImp implements IVehicle {
    
    private IVehicleDao dao;
    
    public VehicleServiceImp() throws DatabaseConnectionException {
        dao = new VehicleDaoImp();
    }

    @Override
    public Vehicle getVehicleById(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException {
        Vehicle vehicle = dao.getVehicleById(vehicleId);
        if (vehicle == null) {
            throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found");
        }
        return vehicle;
    }

    @Override
    public List<Vehicle> getAvailableVehicles() throws DatabaseConnectionException {
        List<Vehicle> vehicles = dao.getAvailableVehicles();
        if (vehicles == null) {
            throw new DatabaseConnectionException("Failed to retrieve available vehicles");
        }
        return vehicles;
    }

    @Override
    public int addVehicle(Vehicle vehicleData) throws InvalidInputException, DatabaseConnectionException {
        if (vehicleData == null || !isValidVehicleData(vehicleData)) {
            throw new InvalidInputException("Invalid vehicle data provided");
        }
        return dao.addVehicle(vehicleData);
    }

    @Override
    public int updateVehicle(Vehicle vehicleData) throws VehicleNotFoundException, InvalidInputException, DatabaseConnectionException {
        if (vehicleData == null || !isValidVehicleData(vehicleData)) {
            throw new InvalidInputException("Invalid vehicle data provided");
        }
        int result = dao.updateVehicle(vehicleData);
        if (result == 0) {
            throw new VehicleNotFoundException("Vehicle with ID " + vehicleData.getVehicleID() + " not found");
        }
        return result;
    }

    @Override
    public int removeVehicle(int vehicleId) throws VehicleNotFoundException, DatabaseConnectionException {
        int result = dao.removeVehicle(vehicleId);
        if (result == 0) {
            throw new VehicleNotFoundException("Vehicle with ID " + vehicleId + " not found");
        }
        return result;
    }

    private boolean isValidVehicleData(Vehicle vehicle) {
        return vehicle.getModel() != null && !vehicle.getModel().isEmpty() &&
               vehicle.getMake() != null && !vehicle.getMake().isEmpty() &&
               vehicle.getRegistrationNumber() != null && !vehicle.getRegistrationNumber().isEmpty() &&
               vehicle.getDailyRate() > 0;
    }
}