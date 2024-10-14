package apap.ti.appointment2206082505.restdto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TreatmentResponseDTO {

    private Long id;
    private String name;
    private Long price;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date createdAt;

    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone = "Asia/Jakarta")
    private Date updatedAt;
}
