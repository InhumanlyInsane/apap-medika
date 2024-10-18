package apap.ti.appointment2206082505.dto.request;

import java.util.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddPatientRequestDTO {
    private String name;
    private String nik;
    private String email;
    private Boolean gender;
    private String birthDate;
    private String birthPlace;
}
