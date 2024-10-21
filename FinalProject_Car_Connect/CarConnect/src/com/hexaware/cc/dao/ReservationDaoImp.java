/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (ReservationDaoImp)
 * Date: 21/10/2024
 */


package com.hexaware.cc.dao;

import java.sql.*;
import java.sql.SQLException;
import java.util.*;

import com.hexaware.cc.entity.Reservation;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.util.DBConnUtil;


public class ReservationDaoImp implements IReservationDao {
	private Connection conn;
	
	public ReservationDaoImp () throws DatabaseConnectionException {
		conn = DBConnUtil.getConnection();

	}

	@Override
	public Reservation getReservationById(int reservationId) {
		Reservation reservation = null;
        try {
            String query = "SELECT * FROM Reservation WHERE ReservationID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reservationId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                reservation = new Reservation();
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setCustomerID(rs.getInt("CustomerID"));
                reservation.setVehicleID(rs.getInt("VehicleID"));
                reservation.setStartDate(rs.getDate("StartDate"));
                reservation.setEndDate(rs.getDate("EndDate"));
                reservation.setTotalCost(rs.getDouble("TotalCost"));
                reservation.setStatus(rs.getString("Status"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservation;
	}

	@Override
	public List<Reservation> getReservationsByCustomerId(int customerId) {
		List<Reservation> reservations = new ArrayList<>();
        try {
            String query = "SELECT * FROM Reservation WHERE CustomerID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, customerId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setCustomerID(rs.getInt("CustomerID"));
                reservation.setVehicleID(rs.getInt("VehicleID"));
                reservation.setStartDate(rs.getDate("StartDate"));
                reservation.setEndDate(rs.getDate("EndDate"));
                reservation.setTotalCost(rs.getDouble("TotalCost"));
                reservation.setStatus(rs.getString("Status"));
                reservations.add(reservation);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reservations;
	}

	@Override
	public int createReservation(Reservation reservationData) {
		int rowsAffected = 0;
        try {
            String query = "INSERT INTO Reservation (CustomerID, VehicleID, StartDate, EndDate, TotalCost, Status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reservationData.getCustomerID());
            pstmt.setInt(2, reservationData.getVehicleID());
            pstmt.setDate(3, new java.sql.Date(reservationData.getStartDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(reservationData.getEndDate().getTime()));
            pstmt.setDouble(5, reservationData.getTotalCost());
            pstmt.setString(6, reservationData.getStatus());
            
            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
	}

	@Override
	public int updateReservation(Reservation reservationData) {
		int rowsAffected = 0;
        try {
            String query = "UPDATE Reservation SET CustomerID = ?, VehicleID = ?, StartDate = ?, EndDate = ?, TotalCost = ?, Status = ? WHERE ReservationID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reservationData.getCustomerID());
            pstmt.setInt(2, reservationData.getVehicleID());
            pstmt.setDate(3, new java.sql.Date(reservationData.getStartDate().getTime()));
            pstmt.setDate(4, new java.sql.Date(reservationData.getEndDate().getTime()));
            pstmt.setDouble(5, reservationData.getTotalCost());
            pstmt.setString(6, reservationData.getStatus());
            pstmt.setInt(7, reservationData.getReservationID());

            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
	}

	@Override
	public int cancelReservation(int reservationId) {
		int rowsAffected = 0;
        try {
            String query = "DELETE FROM Reservation WHERE ReservationID = ?";
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, reservationId);

            rowsAffected = pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }
}
	
	
