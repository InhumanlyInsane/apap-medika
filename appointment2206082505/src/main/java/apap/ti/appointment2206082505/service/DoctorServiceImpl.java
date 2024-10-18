package apap.ti.appointment2206082505.service;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.appointment2206082505.model.Doctor;
import apap.ti.appointment2206082505.repository.DoctorDb;

@Service
public class DoctorServiceImpl implements DoctorService {

    private static final Map<Integer, String> specializationMap = new HashMap<Integer, String>() {{
        put(0, "UMM");  // Dokter umum
        put(1, "GGI");  // Dokter gigi
        put(2, "ANK");  // Spesialis Anak
        put(3, "BDH");  // Bedah
        put(4, "PRE");  // Bedah Plastik, Rekonstruksi, dan Estetik
        put(5, "JPD");  // Jantung dan Pembuluh Darah
        put(6, "KKL");  // Kulit dan Kelamin
        put(7, "MTA");  // Mata
        put(8, "OBG");  // Obstetri dan Ginekologi
        put(9, "PDL");  // Penyakit Dalam
        put(10, "PRU"); // Paru
        put(11, "THT"); // Telinga, Hidung, Tenggorokan, Bedah Kepala Leher
        put(12, "RAD"); // Radiologi
        put(13, "KSJ"); // Kesehatan Jiwa
        put(14, "ANS"); // Anestesi
        put(15, "NRO"); // Neurologi
        put(16, "URO"); // Urologi
    }};
    
    @Autowired
    private DoctorDb doctorDb;

    @Override
    public Doctor addDoctor(Doctor doctor) {
        String specializationCode = specializationMap.getOrDefault(doctor.getSpecialist(), "UNK");
        String nomorUnik = String.format("%03d", new Random().nextInt(1000));
        doctor.setId(specializationCode + nomorUnik);

        return doctorDb.save(doctor);
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return doctorDb.findAll();
    }

    @Override
    public Doctor getDoctorById(String id) {
        return doctorDb.findById(id).get();
    }
}
