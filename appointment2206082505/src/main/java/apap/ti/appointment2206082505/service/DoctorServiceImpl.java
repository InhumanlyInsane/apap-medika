package apap.ti.appointment2206082505.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.appointment2206082505.model.Doctor;
import apap.ti.appointment2206082505.repository.DoctorDb;

@Service
public class DoctorServiceImpl implements DoctorService {
    
    @Autowired
    private DoctorDb doctorDb;

    @Override
    public Doctor addDoctor(Doctor doctor) {
        return doctorDb.save(doctor);
    }
}
