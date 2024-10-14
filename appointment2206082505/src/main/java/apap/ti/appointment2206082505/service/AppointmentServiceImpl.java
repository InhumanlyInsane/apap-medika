package apap.ti.appointment2206082505.service;

import java.util.*;

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
    
    @Autowired
    private AppointmentDb appointmentDb;

    private final WebClient webClient;

    public AppointmentServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8080/api").build();
    }

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

    // DIUTAMAKAN UNTUK FAKER
    @Override
    public Appointment addAppointment(Appointment appointment) {
        return appointmentDb.save(appointment);
    }

}
