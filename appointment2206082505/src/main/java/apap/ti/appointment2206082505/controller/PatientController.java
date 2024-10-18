package apap.ti.appointment2206082505.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import apap.ti.appointment2206082505.service.PatientService;

@Controller
public class PatientController {
    
    @Autowired
    private PatientService patientService;
    
    @GetMapping("/patient/search")
    public String formSearchPatient(Model model) {
        return "form-search-patient";
    }

    @PostMapping("/patient/search/{nik}")
    public String searchPatient(@PathVariable("nik") String nik, Model model) {
        var patient = patientService.searchPatient(nik);
        if (patient != null) {
            model.addAttribute("patient", patient);
            return "detail-patient";
        } else {
            return "patient-not-found";
        }
    }
    
}
