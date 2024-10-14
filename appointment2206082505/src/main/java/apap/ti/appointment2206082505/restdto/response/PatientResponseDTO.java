package apap.ti.appointment2206082505.restdto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class PatientResponseDTO {

    private UUID id;
    private String nik;
    private String name;
    private Boolean gender;
    private String email;
    private String birthPlace;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date birthDate;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AppointmentResponseDTO> appointments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date updatedAt;
}
