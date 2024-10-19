package apap.ti.appointment2206082505.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import apap.ti.appointment2206082505.service.DoctorService;

@Controller
public class DoctorController {
    
    @Autowired
    private DoctorService doctorService;

    @GetMapping("/doctor/all")
    public String viewAllDoctor(Model model) {
        var listDoctor = doctorService.getAllDoctor();

        model.addAttribute("specializationMap", doctorService.getSpecializationMap());
        model.addAttribute("doctorService", doctorService);
        model.addAttribute("listDoctor", listDoctor);
        return "all-doctor";
    }

    @GetMapping("/doctor/{id}")
    public String detailDoctor(@PathVariable ("id") String id, Model model) {
        var doctor = doctorService.getDoctorById(id);
        String schedule = doctorService.convertScheduleToDayNames(doctor.getSchedule());

        model.addAttribute("specializationMap", doctorService.getSpecializationMap());
        model.addAttribute("schedule", schedule);
        model.addAttribute("doctor", doctor);
        return "detail-doctor";
    }
}
