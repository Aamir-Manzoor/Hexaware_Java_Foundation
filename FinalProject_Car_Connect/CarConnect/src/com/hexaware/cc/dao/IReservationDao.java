/*
 * Author: Venkatesh Pai & Amir Manzoor
 * Desc: Carconnect (Reservation Dao)
 * Date: 21/10/2024
 */


package com.hexaware.cc.dao;

import java.util.List;
import com.hexaware.cc.entity.Reservation;

public interface IReservationDao {
    
    Reservation getReservationById(int reservationId);
    
    List<Reservation> getReservationsByCustomerId(int customerId);
    
    int createReservation(Reservation reservationData);
    
    int updateReservation(Reservation reservationData);
    
    int cancelReservation(int reservationId);
}