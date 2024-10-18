package apap.ti.appointment2206082505.controller;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;

import apap.ti.appointment2206082505.dto.AddPatientAppointmentWrapperDTO;
import apap.ti.appointment2206082505.dto.request.AddAppointmentRequestDTO;
import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.model.Doctor;
import apap.ti.appointment2206082505.model.Patient;
import apap.ti.appointment2206082505.service.AppointmentService;
import apap.ti.appointment2206082505.service.DoctorService;
import apap.ti.appointment2206082505.service.PatientService;


@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;
    
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

    @GetMapping("/appointment/{id}")
    public String detailAppointment(@PathVariable("id") String id, Model model) {
        var appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        return "detail-appointment";
    }

    @GetMapping("/appointment/{nik}/create")
    public String formCreateAppointment(@PathVariable("nik") String nik, Model model) {
        var appointmentDTO = new AddAppointmentRequestDTO();
        var patient = patientService.searchPatient(nik);
        List<Doctor> allDoctors = doctorService.getAllDoctor();

        model.addAttribute("appointmentDTO", appointmentDTO);
        model.addAttribute("patient", patient);
        model.addAttribute("allDoctors", allDoctors);

        return "form-create-appointment";
    }

    @PostMapping("/appointment/{nik}/create")
    public String createAppointment(@PathVariable("nik") String nik, @ModelAttribute AddAppointmentRequestDTO appointmentDTO, Model model) {
        var patient = patientService.searchPatient(nik);
        var doctor = doctorService.getDoctorById(appointmentDTO.getDoctorId());

        var appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);

        Date scheduledDate = Date.from(Instant.parse(appointmentDTO.getAppointmentDate()));
        appointment.setDate(scheduledDate);
        appointment.setDiagnosis(null);
        appointment.setTreatments(null);
        appointment.setTotalFee(doctor.getFee());
        appointment.setStatus(0);
        appointmentService.addAppointment(appointment);

        model.addAttribute("responseMessage", 
            String.format("Appointment with ID %s has been successfully created", appointment.getId()));

        return "success-create-appointment";
    }

    @GetMapping("/appointment/create-with-patient")
    public String formCreatePatientAppointment(Model model) {
        var wrapperDTO = new AddPatientAppointmentWrapperDTO();
        List<Doctor> allDoctors = doctorService.getAllDoctor();

        model.addAttribute("wrapperDTO", wrapperDTO);
        model.addAttribute("allDoctors", allDoctors);

        return "form-create-patient-appointment";
    }

    @PostMapping("/appointment/create-with-patient")
    public String createPatientAppointment(@ModelAttribute AddPatientAppointmentWrapperDTO wrapperDTO, Model model) {
        var patientDTO = wrapperDTO.getPatientDTO();
        var appointmentDTO = wrapperDTO.getAppointmentDTO();
        var doctor = doctorService.getDoctorById(appointmentDTO.getDoctorId());

        var patient = new Patient();
        patient.setName(patientDTO.getName());
        patient.setNik(patientDTO.getNik());
        patient.setEmail(patientDTO.getEmail());
        patient.setGender(patientDTO.getGender());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localBirthDate = LocalDate.parse(patientDTO.getBirthDate(), formatter);
        Date parsedBirthDate = Date.from(localBirthDate.atStartOfDay(ZoneId.systemDefault()).toInstant());
        patient.setBirthDate(parsedBirthDate);
        patient.setBirthPlace(patientDTO.getBirthPlace());

        var appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        Date scheduledDate = Date.from(Instant.parse(appointmentDTO.getAppointmentDate()));
        appointment.setDate(scheduledDate);
        appointment.setDiagnosis(null);
        appointment.setTreatments(null);
        appointment.setTotalFee(doctor.getFee());
        appointment.setStatus(0);
        
        List<Appointment> listAppointment = new ArrayList<>();
        listAppointment.add(appointment);
        patient.setAppointments(listAppointment);

        patientService.addPatient(patient);
        appointmentService.addAppointment(appointment);

        model.addAttribute("responseMessage",
            String.format("Patient %s and Appointment %s has been successfully created", patient.getName(), appointment.getId()));

        return "success-create-patient-appointment";
    }

}