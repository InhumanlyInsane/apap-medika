package apap.ti.appointment2206082505.service;

import java.util.List;

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

    @Override
    public Patient searchPatient(String nik) {
        List<Patient> allPatient = patientDb.findAll();
        for (Patient patient : allPatient) {
            if (patient.getNik().equals(nik)) {
                return patient;
            }
        }

        return null;
    }
}
