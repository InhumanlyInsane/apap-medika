package apap.ti.appointment2206082505.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import apap.ti.appointment2206082505.model.Appointment;

@Repository
public interface AppointmentDb extends JpaRepository<Appointment, String> {

        @Query("SELECT a FROM Appointment a")
        List<Appointment> findAllAppointments();

        @Query("SELECT MONTH(a.date) as month, COUNT(a) as count FROM Appointment a " +
                "WHERE YEAR(a.date) = :year GROUP BY MONTH(a.date)")
        List<Object[]> getMonthlyAppointmentCounts(@Param("year") int year);

        @Query("SELECT QUARTER(a.date) as quarter, COUNT(a) as count FROM Appointment a " +
                "WHERE YEAR(a.date) = :year GROUP BY QUARTER(a.date)")
        List<Object[]> getQuarterlyAppointmentCounts(@Param("year") int year);

        @Query("SELECT a FROM Appointment a WHERE a.date BETWEEN :startDate AND :endDate")
        List<Appointment> findByDateBetween(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

}
