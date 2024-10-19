package apap.ti.appointment2206082505.service;

import java.util.*;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import apap.ti.appointment2206082505.model.Doctor;
import apap.ti.appointment2206082505.repository.DoctorDb;

@Service
public class DoctorServiceImpl implements DoctorService {

    private static final Map<Integer, String> specializationMapKode = new HashMap<Integer, String>() {{
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

    private static final Map<Integer, String> specializationMap = new HashMap<Integer, String>() {{
        put(0, "Dokter Umum");
        put(1, "Dokter Gigi");
        put(2, "Spesialis Anak");
        put(3, "Bedah");
        put(4, "Bedah Plastik, Rekonstruksi, dan Estetik");
        put(5, "Jantung dan Pembuluh Darah");
        put(6, "Kulit dan Kelamin");
        put(7, "Mata");
        put(8, "Obstetri dan Ginekologi");
        put(9, "Penyakit Dalam");
        put(10, "Paru");
        put(11, "Telinga, Hidung, Tenggorokan, Bedah Kepala Leher");
        put(12, "Radiologi");
        put(13, "Kesehatan Jiwa");
        put(14, "Anestesi");
        put(15, "Neurologi");
        put(16, "Urologi");
    }};

    private static final Map<Integer, String> dayMap = new HashMap<Integer, String>() {{
        put(1, "Monday");
        put(2, "Tuesday");
        put(3, "Wednesday");
        put(4, "Thursday");
        put(5, "Friday");
        put(6, "Saturday");
        put(7, "Sunday");
    }};
    
    @Autowired
    private DoctorDb doctorDb;

    @Override
    public Doctor addDoctor(Doctor doctor) {
        String specializationCode = specializationMapKode.getOrDefault(doctor.getSpecialist(), "UNK");
        String nomorUnik = String.format("%03d", new Random().nextInt(1000));
        doctor.setId(specializationCode + nomorUnik);

        return doctorDb.save(doctor);
    }

    @Override
    public Doctor updateDoctor(Doctor doctor) {
        Doctor existingDoctor = getDoctorById(doctor.getId());

        if (existingDoctor != null) {
            existingDoctor.setName(doctor.getName());
            existingDoctor.setEmail(doctor.getEmail());
            existingDoctor.setGender(doctor.getGender());
            existingDoctor.setSpecialist(doctor.getSpecialist());
            existingDoctor.setYearsOfExperience(doctor.getYearsOfExperience());
            existingDoctor.setFee(doctor.getFee());
            existingDoctor.setSchedule(doctor.getSchedule());
            doctorDb.save(existingDoctor);
            return existingDoctor;
        }

        return null;
    }

    @Override
    public List<Doctor> getAllDoctor() {
        return doctorDb.findAll();
    }

    @Override
    public Doctor getDoctorById(String id) {
        return doctorDb.findById(id).get();
    }

    @Override
    public Map<Integer, String> getSpecializationMap() {
        return specializationMap;
    }

    @Override
    public String convertScheduleToDayNames(List<Integer> schedule) {
        return schedule.stream()
                .map(dayMap::get)
                .collect(Collectors.joining(", "));
    }
}
