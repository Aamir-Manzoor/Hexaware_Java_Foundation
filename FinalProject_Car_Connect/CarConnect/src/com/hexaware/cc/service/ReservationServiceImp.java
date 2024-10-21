
/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (ReservationServiceImp)
 * Date: 21/10/2024
 */

package com.hexaware.cc.service;

import java.util.List;
import com.hexaware.cc.dao.IReservationDao;
import com.hexaware.cc.dao.ReservationDaoImp;
import com.hexaware.cc.entity.Reservation;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.ReservationException;

public class ReservationServiceImp implements IReservation {
    
    private IReservationDao reservationDao;
    
    public ReservationServiceImp() throws DatabaseConnectionException {
        this.reservationDao = new ReservationDaoImp();
    }

    @Override
    public Reservation getReservationById(int reservationId) throws ReservationException, DatabaseConnectionException {
        Reservation reservation = reservationDao.getReservationById(reservationId);
        if (reservation == null) {
            throw new ReservationException("Reservation with ID " + reservationId + " not found");
        }
        return reservation;
    }

    @Override
    public List<Reservation> getReservationsByCustomerId(int customerId) throws DatabaseConnectionException {
        List<Reservation> reservations = reservationDao.getReservationsByCustomerId(customerId);
        if (reservations == null) {
            throw new DatabaseConnectionException("Failed to retrieve reservations for customer ID " + customerId);
        }
        return reservations;
    }

    @Override
    public int createReservation(Reservation reservationData) throws InvalidInputException, ReservationException, DatabaseConnectionException {
        if (reservationData == null || !isValidReservationData(reservationData)) {
            throw new InvalidInputException("Invalid reservation data provided");
        }
        int result = reservationDao.createReservation(reservationData);
        if (result == 0) {
            throw new ReservationException("Failed to create reservation");
        }
        return result;
    }

    @Override
    public int updateReservation(Reservation reservationData) throws InvalidInputException, ReservationException, DatabaseConnectionException {
        if (reservationData == null || !isValidReservationData(reservationData)) {
            throw new InvalidInputException("Invalid reservation data provided");
        }
        int result = reservationDao.updateReservation(reservationData);
        if (result == 0) {
            throw new ReservationException("Reservation with ID " + reservationData.getReservationID() + " not found");
        }
        return result;
    }

    @Override
    public int cancelReservation(int reservationId) throws ReservationException, DatabaseConnectionException {
        int result = reservationDao.cancelReservation(reservationId);
        if (result == 0) {
            throw new ReservationException("Reservation with ID " + reservationId + " not found or already cancelled");
        }
        return result;
    }

    private boolean isValidReservationData(Reservation reservation) {
        return reservation.getCustomerID() > 0 &&
               reservation.getVehicleID() > 0 &&
               reservation.getStartDate() != null &&
               reservation.getEndDate() != null &&
               reservation.getStartDate().before(reservation.getEndDate()) &&
               reservation.getTotalCost() > 0;
    }
}