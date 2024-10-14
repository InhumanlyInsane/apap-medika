package apap.ti.appointment2206082505.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import apap.ti.appointment2206082505.model.Appointment;

@Repository
public interface AppointmentDb extends JpaRepository<Appointment, UUID> {
    
    @Query("SELECT a FROM Appointment a")
    List<Appointment> findAllAppointments();

}
