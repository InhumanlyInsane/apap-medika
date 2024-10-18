package apap.ti.appointment2206082505.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddAppointmentRequestDTO {
    private String doctorId;
    private String appointmentDate;
}
