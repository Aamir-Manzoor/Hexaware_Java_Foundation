/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (ReservationServiceImp Test)
 * Date: 21/10/2024
 */


package com.hexaware.cc.test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import com.hexaware.cc.entity.Reservation;
import com.hexaware.cc.exception.DatabaseConnectionException;
import com.hexaware.cc.exception.InvalidInputException;
import com.hexaware.cc.exception.ReservationException;
import com.hexaware.cc.service.IReservation;
import com.hexaware.cc.service.ReservationServiceImp;
import java.util.List;

class ReservationServiceImpTest {
    
    static IReservation service;
    
    @BeforeAll
    public static void beforeAll() throws DatabaseConnectionException {
        service = new ReservationServiceImp();
    }
    
    @Test
    void testReservationServiceImp() {
        assertNotNull(service, "Service should not be null");
    }
    
    @Test
    void testGetReservationById() throws ReservationException, DatabaseConnectionException {
        int reservationId = 10;
        Reservation reservation = service.getReservationById(reservationId);
        
        assertNotNull(reservation, "Reservation should not be null");
        assertEquals(reservationId, reservation.getReservationID(), "Reservation ID should match");
    }
    
    @Test
    void testGetReservationsByCustomerId() throws DatabaseConnectionException {
        int customerId = 10;
        List<Reservation> reservations = service.getReservationsByCustomerId(customerId);
        
        assertNotNull(reservations, "Reservations list should not be null");
        assertFalse(reservations.isEmpty(), "Reservations list should not be empty");
    }
    
    @Test
    void testCreateReservation() throws InvalidInputException, ReservationException, DatabaseConnectionException {
        Reservation reservation = new Reservation();
        
        int result = service.createReservation(reservation);
        assertTrue(result > 0, "Reservation should be created successfully");
    }
    
    @Test
    void testUpdateReservation() throws InvalidInputException, ReservationException, DatabaseConnectionException {
        Reservation reservation = new Reservation();
        reservation.setReservationID(10);
       
        
        int result = service.updateReservation(reservation);
        assertEquals(1, result, "Reservation should be updated successfully");
    }
    
    @Test
    void testCancelReservation() throws ReservationException, DatabaseConnectionException {
        int reservationId = 10;
        
        int result = service.cancelReservation(reservationId);
        assertEquals(1, result, "Reservation should be cancelled successfully");
    }
}