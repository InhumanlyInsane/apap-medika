package apap.ti.appointment2206082505.restdto.response;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class BaseResponseDTO<T> {
    private int status;
    private String message;
    @JsonFormat(shape = JsonFormat.Shape.STRING, timezone="Asia/Jakarta")
    private Date timestamp;
    private T data;
}