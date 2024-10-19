package apap.ti.appointment2206082505.restcontroller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import apap.ti.appointment2206082505.restdto.response.AppointmentResponseDTO;
import apap.ti.appointment2206082505.restdto.response.BaseResponseDTO;
import apap.ti.appointment2206082505.restservice.AppointmentRestService;

@RestController
@RequestMapping("/api/appointment")
public class AppointmentRestController {
    
    @Autowired
    AppointmentRestService appointmentRestService;

    @GetMapping("/all")
    public ResponseEntity<?> listAppointment() {
        var baseResponseDTO = new BaseResponseDTO<>();
        List<AppointmentResponseDTO> listAppointment = appointmentRestService.getAllAppointment();

        baseResponseDTO.setStatus(HttpStatus.OK.value());
        baseResponseDTO.setData(listAppointment);
        baseResponseDTO.setMessage(String.format("List appointment berhasil ditemukan"));
        baseResponseDTO.setTimestamp(new Date());
        return new ResponseEntity<>(baseResponseDTO, HttpStatus.OK);
    }

    @GetMapping("/stat")
    public ResponseEntity<List<Integer>> getAppointmentStats(
            @RequestParam String period,
            @RequestParam int year) {
        List<Integer> stats = appointmentRestService.getAppointmentStats(period, year);
        return ResponseEntity.ok(stats);
    }
    
}
