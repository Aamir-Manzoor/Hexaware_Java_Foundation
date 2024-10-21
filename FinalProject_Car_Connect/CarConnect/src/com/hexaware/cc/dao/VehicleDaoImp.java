/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (VehicleDaoImp)
 * Date: 21/10/2024
 */

package com.hexaware.cc.dao;

import java.sql.*;
import java.sql.SQLException;
import java.util.List;
import com.hexaware.cc.entity.Vehicle;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.util.DBConnUtil;
import java.util.ArrayList;

public class VehicleDaoImp implements IVehicleDao {
	private Connection conn;
	
	public VehicleDaoImp () throws DatabaseConnectionException {
		conn = DBConnUtil.getConnection();

	}

	@Override
	public Vehicle getVehicleById(int vehicleId) {
		try {
            String query = "SELECT * FROM Vehicle WHERE VehicleID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, vehicleId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleID(rs.getInt("VehicleID"));
                vehicle.setModel(rs.getString("Model"));
                vehicle.setMake(rs.getString("Make"));
                vehicle.setYear(rs.getInt("Year"));
                vehicle.setColor(rs.getString("Color"));
                vehicle.setRegistrationNumber(rs.getString("RegistrationNumber"));
                vehicle.setAvailability(rs.getBoolean("Availability"));
                vehicle.setDailyRate(rs.getDouble("DailyRate"));
                return vehicle;
            }
	}catch (SQLException e) {
		e.printStackTrace();
	}
		return null;
		}

	@Override
	public List<Vehicle> getAvailableVehicles() {
		List<Vehicle> availableVehicles = new ArrayList<>();
        try {
            String query = "SELECT * FROM Vehicle WHERE Availability = true";
            PreparedStatement pstmt = conn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleID(rs.getInt("VehicleID"));
                vehicle.setModel(rs.getString("Model"));
                vehicle.setMake(rs.getString("Make"));
                vehicle.setYear(rs.getInt("Year"));
                vehicle.setColor(rs.getString("Color"));
                vehicle.setRegistrationNumber(rs.getString("RegistrationNumber"));
                vehicle.setAvailability(rs.getBoolean("Availability"));
                vehicle.setDailyRate(rs.getDouble("DailyRate"));
                availableVehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return availableVehicles;
	}

	@Override
	public int addVehicle(Vehicle vehicleData) {
		try {
            String query = "INSERT INTO Vehicle (Model, Make, Year, Color, RegistrationNumber, Availability, DailyRate) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, vehicleData.getModel());
            pstmt.setString(2, vehicleData.getMake());
            pstmt.setInt(3, vehicleData.getYear());
            pstmt.setString(4, vehicleData.getColor());
            pstmt.setString(5, vehicleData.getRegistrationNumber());
            pstmt.setBoolean(6, vehicleData.isAvailable());
            pstmt.setDouble(7, vehicleData.getDailyRate());

            return pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
	}

	@Override
	public int updateVehicle(Vehicle vehicleData) {
		try {
            String query = "UPDATE Vehicle SET Model = ?, Make = ?, Year = ?, Color = ?, RegistrationNumber = ?, Availability = ?, DailyRate = ? WHERE VehicleID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, vehicleData.getModel());
            pstmt.setString(2, vehicleData.getMake());
            pstmt.setInt(3, vehicleData.getYear());
            pstmt.setString(4, vehicleData.getColor());
            pstmt.setString(5, vehicleData.getRegistrationNumber());
            pstmt.setBoolean(6, vehicleData.isAvailable());
            pstmt.setDouble(7, vehicleData.getDailyRate());
            pstmt.setInt(8, vehicleData.getVehicleID());

            return pstmt.executeUpdate();  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
	}

	@Override
	public int removeVehicle(int vehicleId) {
		try {
            String query = "DELETE FROM Vehicle WHERE VehicleID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, vehicleId);
            return pstmt.executeUpdate();  
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
