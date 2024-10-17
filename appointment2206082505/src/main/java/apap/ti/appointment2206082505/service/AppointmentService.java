package apap.ti.appointment2206082505.service;

import java.util.*;

import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.restdto.response.AppointmentResponseDTO;

public interface AppointmentService {
    List<AppointmentResponseDTO> getAllAppointmentsFromRest() throws Exception;
    Appointment getAppointmentById(String id);    
    Appointment addAppointment(Appointment appointment);
}
