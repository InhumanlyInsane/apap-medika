package apap.ti.appointment2206082505.service;

import java.util.*;

import apap.ti.appointment2206082505.model.Doctor;

public interface DoctorService {
    Doctor addDoctor(Doctor doctor);
    List<Doctor> getAllDoctor();
    Doctor getDoctorById(String id);
    Map<Integer, String> getSpecializationMap();
    String convertScheduleToDayNames(List<Integer> schedule);
    Doctor updateDoctor(Doctor doctor);
    void deleteDoctor(Doctor doctor);
}
