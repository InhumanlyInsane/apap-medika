package apap.ti.appointment2206082505.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import apap.ti.appointment2206082505.model.Treatment;

@Repository
public interface TreatmentDb extends JpaRepository<Treatment, Long> {
    
}
