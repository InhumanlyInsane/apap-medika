package apap.ti.appointment2206082505.service;

import java.util.*;

import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.restdto.response.AppointmentResponseDTO;

public interface AppointmentService {
    List<AppointmentResponseDTO> getAllAppointmentsFromRest() throws Exception;
    List<Appointment> getAllAppointments();
    Appointment getAppointmentById(String id);    
    Appointment addAppointment(Appointment appointment);
    Appointment updateAppointmentDoctorDate(Appointment appointment);
    Appointment updateAppointmentDiagnosisTreatment(Appointment appointment);
    Appointment updateStatus(Appointment appointment, int status);
    void deleteAppointment(Appointment appointment);
    List<Appointment> getAppointmentsByDateRange(Date startDate, Date endDate);
}
