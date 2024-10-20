package apap.ti.appointment2206082505.restservice;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.repository.AppointmentDb;
import apap.ti.appointment2206082505.restdto.response.AppointmentResponseDTO;
import apap.ti.appointment2206082505.restdto.response.DoctorResponseDTO;
import apap.ti.appointment2206082505.restdto.response.PatientResponseDTO;
import apap.ti.appointment2206082505.restdto.response.TreatmentResponseDTO;

@Service
@Transactional
public class AppointmentRestServiceImpl implements AppointmentRestService {

    @Autowired
    AppointmentDb appointmentDb;

    @Override
    public List<AppointmentResponseDTO> getAllAppointment() {
        var listAppointment = appointmentDb.findAll();
        var listAppointmentResponseDTO = new ArrayList<AppointmentResponseDTO>();
        listAppointment.forEach(appointment -> {
            var appointmentResponseDTO = appointmentToAppointmentResponseDTO(appointment);
            listAppointmentResponseDTO.add(appointmentResponseDTO);
        });

        return listAppointmentResponseDTO;
        
    }
    

    private AppointmentResponseDTO appointmentToAppointmentResponseDTO(Appointment appointment) {
        var appointmentResponseDTO = new AppointmentResponseDTO();
        appointmentResponseDTO.setId(appointment.getId());
        appointmentResponseDTO.setDiagnosis(appointment.getDiagnosis());
        appointmentResponseDTO.setTotalFee(appointment.getTotalFee());
        appointmentResponseDTO.setStatus(appointment.getStatus());
        appointmentResponseDTO.setDate(appointment.getDate());
        appointmentResponseDTO.setCreatedAt(appointment.getCreatedAt());
        appointmentResponseDTO.setUpdatedAt(appointment.getUpdatedAt());

        if (appointment.getTreatments() != null) {
            var listAppointmentResponseDTO = new ArrayList<TreatmentResponseDTO>();
            appointment.getTreatments().forEach(treatment -> {
                var treatmentResponseDTO = new TreatmentResponseDTO();
                treatmentResponseDTO.setId(treatment.getId());
                treatmentResponseDTO.setName(treatment.getName());
                treatmentResponseDTO.setPrice(treatment.getPrice());
                treatmentResponseDTO.setCreatedAt(treatment.getCreatedAt());
                treatmentResponseDTO.setUpdatedAt(treatment.getUpdatedAt());
                listAppointmentResponseDTO.add(treatmentResponseDTO);
            });
            appointmentResponseDTO.setTreatments(listAppointmentResponseDTO);
        }

        var doctorResponseDTO = new DoctorResponseDTO();
        doctorResponseDTO.setId(appointment.getDoctor().getId());
        doctorResponseDTO.setName(appointment.getDoctor().getName());
        doctorResponseDTO.setSpecialist(appointment.getDoctor().getSpecialist());
        doctorResponseDTO.setEmail(appointment.getDoctor().getEmail());
        doctorResponseDTO.setGender(appointment.getDoctor().getGender());
        doctorResponseDTO.setYearsOfExperience(appointment.getDoctor().getYearsOfExperience());
        doctorResponseDTO.setFee(appointment.getDoctor().getFee());
        doctorResponseDTO.setCreatedAt(appointment.getDoctor().getCreatedAt());
        doctorResponseDTO.setUpdatedAt(appointment.getDoctor().getUpdatedAt());
        appointmentResponseDTO.setDoctor(doctorResponseDTO);

        var patientResponseDTO = new PatientResponseDTO();
        patientResponseDTO.setId(appointment.getPatient().getId());
        patientResponseDTO.setNik(appointment.getPatient().getNik());
        patientResponseDTO.setName(appointment.getPatient().getName());
        patientResponseDTO.setGender(appointment.getPatient().getGender());
        patientResponseDTO.setEmail(appointment.getPatient().getEmail());
        patientResponseDTO.setBirthDate(appointment.getPatient().getBirthDate());
        patientResponseDTO.setBirthPlace(appointment.getPatient().getBirthPlace());
        patientResponseDTO.setCreatedAt(appointment.getPatient().getCreatedAt());
        patientResponseDTO.setUpdatedAt(appointment.getPatient().getUpdatedAt());
        appointmentResponseDTO.setPatient(patientResponseDTO);

        return appointmentResponseDTO;
    }

    @Override
    public List<Integer> getAppointmentStats(String period, int year) {
        if ("monthly".equals(period)) {
            return getMonthlyStats(year);
        } else if ("quarterly".equals(period)) {
            return getQuarterlyStats(year);
        }
        throw new IllegalArgumentException("Invalid period: " + period);
    }

    private List<Integer> getMonthlyStats(int year) {
        List<Integer> monthlyStats = new ArrayList<>(Collections.nCopies(12, 0));
        List<Object[]> results = appointmentDb.getMonthlyAppointmentCounts(year);
        
        for (Object[] result : results) {
            int month = ((Number) result[0]).intValue() - 1; // Adjust for 0-based index
            int count = ((Number) result[1]).intValue();
            monthlyStats.set(month, count);
        }
        
        return monthlyStats;
    }

    private List<Integer> getQuarterlyStats(int year) {
        List<Integer> quarterlyStats = new ArrayList<>(Collections.nCopies(4, 0));
        List<Object[]> results = appointmentDb.getQuarterlyAppointmentCounts(year);
        
        for (Object[] result : results) {
            int quarter = ((Number) result[0]).intValue() - 1; // Adjust for 0-based index
            int count = ((Number) result[1]).intValue();
            quarterlyStats.set(quarter, count);
        }
        
        return quarterlyStats;
    }


}
