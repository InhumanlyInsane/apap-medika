package apap.ti.appointment2206082505.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.appointment2206082505.model.Patient;
import apap.ti.appointment2206082505.repository.PatientDb;

@Service
public class PatientServiceImpl implements PatientService {
    
    @Autowired
    private PatientDb patientDb;

    @Override
    public Patient addPatient(Patient patient) {
        return patientDb.save(patient);
    }
}
