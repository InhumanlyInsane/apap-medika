package apap.ti.appointment2206082505.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class AppointmentController {
    
    @GetMapping("/")
    private String home(Model model) {
        return "home";
    }
}
