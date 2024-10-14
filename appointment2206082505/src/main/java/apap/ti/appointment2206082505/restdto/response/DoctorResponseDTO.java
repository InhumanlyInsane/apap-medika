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
public class DoctorResponseDTO {

    private UUID id;
    private String name;
    private Integer specialist;
    private String email;
    private Boolean gender;
    private Integer yearsOfExperience;
    private Long fee;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<Integer> schedule;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<AppointmentResponseDTO> appointments;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date updatedAt;
}
