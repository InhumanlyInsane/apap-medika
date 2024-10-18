package apap.ti.appointment2206082505.dto;

import apap.ti.appointment2206082505.dto.request.AddAppointmentRequestDTO;
import apap.ti.appointment2206082505.dto.request.AddPatientRequestDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddPatientAppointmentWrapperDTO {
    private AddPatientRequestDTO patientDTO;
    private AddAppointmentRequestDTO appointmentDTO;
}