package apap.ti.appointment2206082505.repository;

import java.util.*;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.appointment2206082505.model.Patient;

@Repository
public interface PatientDb extends JpaRepository<Patient, UUID> {

}
