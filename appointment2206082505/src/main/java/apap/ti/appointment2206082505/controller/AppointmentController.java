package apap.ti.appointment2206082505.controller;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import apap.ti.appointment2206082505.service.AppointmentService;


@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;
    
    @GetMapping("/")
    private String home(Model model) {
        return "home";
    }

    @GetMapping("/appointment/all")
    public String allAppointment(Model model) {
        try {
            var listAppointment = appointmentService.getAllAppointmentsFromRest();
            model.addAttribute("listAppointment", listAppointment);
            return "all-appointment";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "response-error-rest";
        }
    }

}
