package apap.ti.appointment2206082505.dto.request;

import java.util.*;

import apap.ti.appointment2206082505.model.Treatment;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateAppointmentRequestDTO extends AddAppointmentRequestDTO {
    @NotNull(message = "ID proyek tidak boleh kosong")
    private String id;

    private String diagnosis;
    private List<Treatment> treatments;
}
