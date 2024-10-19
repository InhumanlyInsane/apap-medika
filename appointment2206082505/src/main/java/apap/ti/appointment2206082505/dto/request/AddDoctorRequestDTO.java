package apap.ti.appointment2206082505.dto.request;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddDoctorRequestDTO {
    private String name;
    private String email;
    private Boolean gender;
    private String specialization;
    private Integer yearsOfExperience;
    private List<String> schedule;
    private Long fee;
}
