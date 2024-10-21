/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (ReportGeneration)
 * Date: 21/10/2024
 */

package com.hexaware.cc.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.hexaware.cc.entity.*;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.util.DBConnUtil;

public class ReportGenerator {
	private final Connection connection;

    public ReportGenerator() throws DatabaseConnectionException {
        this.connection = DBConnUtil.getConnection();  
    }

    public Map<String, Object> generateReservationSummary(Date startDate, Date endDate) {
        Map<String, Object> summary = new HashMap<>();
        try {
            String query = """
                SELECT COUNT(*) as total_reservations,
                       SUM(TotalCost) as total_revenue,
                       AVG(TotalCost) as average_cost
                FROM Reservation
                WHERE StartDate >= ? AND EndDate <= ?
                """;
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            pstmt.setDate(1, new java.sql.Date(startDate.getTime()));
            pstmt.setDate(2, new java.sql.Date(endDate.getTime()));
            
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                summary.put("totalReservations", rs.getInt("total_reservations"));
                summary.put("totalRevenue", rs.getDouble("total_revenue"));
                summary.put("averageCost", rs.getDouble("average_cost"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return summary;
    }

    public List<Vehicle> generateVehicleUtilizationReport() {
        List<Vehicle> highUtilizationVehicles = new ArrayList<>();
        try {
            String query = """
                SELECT v.*, COUNT(r.ReservationID) as reservation_count
                FROM Vehicle v
                LEFT JOIN Reservation r ON v.VehicleID = r.VehicleID
                GROUP BY v.VehicleID
                HAVING reservation_count > 5
                ORDER BY reservation_count DESC
                """;
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Vehicle vehicle = new Vehicle();
                vehicle.setVehicleID(rs.getInt("VehicleID"));
                vehicle.setModel(rs.getString("Model"));
                vehicle.setMake(rs.getString("Make"));
                vehicle.setYear(rs.getInt("Year"));
                vehicle.setDailyRate(rs.getDouble("DailyRate"));
                vehicle.setAvailability(rs.getBoolean("Availability"));
                highUtilizationVehicles.add(vehicle);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return highUtilizationVehicles;
    }

    public Map<String, Double> generateRevenueByVehicleType() {
        Map<String, Double> revenueByType = new HashMap<>();
        try {
            String query = """
                SELECT v.Make, SUM(r.TotalCost) as total_revenue
                FROM Vehicle v
                JOIN Reservation r ON v.VehicleID = r.VehicleID
                GROUP BY v.Make
                """;
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                revenueByType.put(
                    rs.getString("Make"),
                    rs.getDouble("total_revenue")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return revenueByType;
    }

    public List<Customer> generateTopCustomersReport() {
        List<Customer> topCustomers = new ArrayList<>();
        try {
            String query = """
                SELECT c.*, COUNT(r.ReservationID) as reservation_count,
                       SUM(r.TotalCost) as total_spent
                FROM Customer c
                JOIN Reservation r ON c.CustomerID = r.CustomerID
                GROUP BY c.CustomerID
                ORDER BY total_spent DESC
                LIMIT 10
                """;
            
            PreparedStatement pstmt = connection.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomerID(rs.getInt("CustomerID"));
                customer.setFirstName(rs.getString("FirstName"));
                customer.setLastName(rs.getString("LastName"));
                customer.setEmail(rs.getString("Email"));
                topCustomers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topCustomers;
    }
}