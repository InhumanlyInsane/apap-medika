package apap.ti.appointment2206082505.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(callSuper=false)
public class UpdateDoctorRequestDTO extends AddDoctorRequestDTO {
    private String id;
}
