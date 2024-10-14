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
public class AppointmentResponseDTO {

    private String id;
    private String diagnosis;
    private Integer status;
    private Long totalFee;
    
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private DoctorResponseDTO doctor;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PatientResponseDTO patient;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<TreatmentResponseDTO> treatments;

    @JsonFormat(pattern = "dd-MM-yyyy")
    private Date date;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date updatedAt;
}
