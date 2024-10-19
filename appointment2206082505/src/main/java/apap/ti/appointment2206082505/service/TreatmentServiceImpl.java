package apap.ti.appointment2206082505.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.appointment2206082505.model.Treatment;
import apap.ti.appointment2206082505.repository.TreatmentDb;

@Service
public class TreatmentServiceImpl implements TreatmentService {

    @Autowired
    private TreatmentDb treatmentDb;

    @Override
    public Treatment addTreatment(Treatment treatment) {
        return treatmentDb.save(treatment);
    }
    
    @Override
    public List<Treatment> getAllTreatment() {
        return treatmentDb.findAll();
    }
    
}
