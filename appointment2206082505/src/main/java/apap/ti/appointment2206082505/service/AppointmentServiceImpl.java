package apap.ti.appointment2206082505.service;

import java.util.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.repository.AppointmentDb;
import apap.ti.appointment2206082505.restdto.response.AppointmentResponseDTO;
import apap.ti.appointment2206082505.restdto.response.BaseResponseDTO;

@Service
public class AppointmentServiceImpl implements AppointmentService{

    private final AppointmentDb appointmentDb;
    private final WebClient webClient;

    @Autowired
    public AppointmentServiceImpl(AppointmentDb appointmentDb, WebClient.Builder webClientBuilder) {
        this.appointmentDb = appointmentDb;
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api").build();
    }

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

    @Override
    public List<AppointmentResponseDTO> getAllAppointmentsFromRest() throws Exception {
        var response = webClient
            .get()
            .uri("/appointment/all")
            .retrieve()
            .bodyToMono(new ParameterizedTypeReference<BaseResponseDTO<List<AppointmentResponseDTO>>>() {})
            .block();

        if (response == null) {
            throw new Exception("Failed consume API getAllAppointments");
        }
        if (response.getStatus() != 200) {
            throw new Exception(response.getMessage());
        }

        return response.getData();
    }

    @Override
    public List<Appointment> getAllAppointments() {
        return appointmentDb.findAll();
    }

    @Override
    public Appointment getAppointmentById(String id) {
        return appointmentDb.findById(id).get();
    }

    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentDb.save(adjustId(appointment));
    }

    @Override
    public Appointment updateAppointmentDoctorDate(Appointment appointment) {
        Appointment getAppointment = getAppointmentById(appointment.getId());

        if (getAppointment != null) {
            getAppointment.setDoctor(appointment.getDoctor());
            getAppointment.setDate(appointment.getDate());
            appointmentDb.save(getAppointment);
            return getAppointment;
        }

        return null;
    }

    @Override
    public Appointment updateAppointmentDiagnosisTreatment(Appointment appointment) {
        Appointment getAppointment = getAppointmentById(appointment.getId());

        if (getAppointment != null) {
            getAppointment.setDiagnosis(appointment.getDiagnosis());
            getAppointment.setTreatments(appointment.getTreatments());
            appointmentDb.save(getAppointment);
            return getAppointment;
        }

        return null;
    }

    @Override
    public Appointment updateStatus(Appointment appointment, int status) {
        Appointment getAppointment = getAppointmentById(appointment.getId());
        getAppointment.setStatus(status);
        appointmentDb.save(getAppointment);

        return getAppointment;
    }

    private Appointment adjustId(Appointment appointment) {
        String specializationCode = specializationMap.getOrDefault(appointment.getDoctor().getSpecialist(), "UNK");
        String datePart = new SimpleDateFormat("ddMM").format(appointment.getDate());
        String counterPart = "";

        List<Appointment> existingAppointments = appointmentDb.findAll();
        boolean idUpdated = false;
        for (Appointment existingAppointment : existingAppointments) {
            LocalDate localDateExisting = existingAppointment.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate localDateNew = appointment.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            int compareValue = localDateExisting.compareTo(localDateNew);

            if (compareValue == 0) {
                String existingId = existingAppointment.getId();
                counterPart = String.format("%03d", Integer.parseInt(existingId.substring(existingId.length() - 3)) + 1);
                idUpdated = true;
            }
        }
        if (!idUpdated) {
            counterPart = "001";
        }

        appointment.setId(specializationCode + datePart + counterPart);

        return appointment;
    }

    @Override
    public void deleteAppointment(Appointment appointment) {
        appointmentDb.delete(appointment);
    }

    @Override
    public List<Appointment> getAppointmentsByDateRange(Date startDate, Date endDate) {
        return appointmentDb.findByDateBetween(startDate, endDate);
    }

}
