/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (ReservationService)
 * Date: 21/10/2024
 */

package com.hexaware.cc.service;

import java.util.List;
import com.hexaware.cc.entity.Reservation;
import com.hexaware.cc.exception.ReservationException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.DatabaseConnectionException;

public interface IReservation {
    
    Reservation getReservationById(int reservationId) throws ReservationException, DatabaseConnectionException;
    
    List<Reservation> getReservationsByCustomerId(int customerId) throws DatabaseConnectionException;
    
    int createReservation(Reservation reservationData) throws InvalidInputException, ReservationException, DatabaseConnectionException;
    
    int updateReservation(Reservation reservationData) throws InvalidInputException, ReservationException, DatabaseConnectionException;
    
    int cancelReservation(int reservationId) throws ReservationException, DatabaseConnectionException;
}