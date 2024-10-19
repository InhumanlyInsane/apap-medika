package apap.ti.appointment2206082505.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;

import apap.ti.appointment2206082505.dto.AddPatientAppointmentWrapperDTO;
import apap.ti.appointment2206082505.dto.request.AddAppointmentRequestDTO;
import apap.ti.appointment2206082505.dto.request.UpdateAppointmentRequestDTO;
import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.model.Doctor;
import apap.ti.appointment2206082505.model.Patient;
import apap.ti.appointment2206082505.model.Treatment;
import apap.ti.appointment2206082505.service.AppointmentService;
import apap.ti.appointment2206082505.service.DoctorService;
import apap.ti.appointment2206082505.service.PatientService;
import apap.ti.appointment2206082505.service.TreatmentService;


@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private TreatmentService treatmentService;
    
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

    @GetMapping("/appointment/{id}/update")
    public String formUpdateAppointment(@PathVariable("id") String id, Model model) {
        var appointment = appointmentService.getAppointmentById(id);

        // Check if the appointment is within 24 hours
        LocalDateTime appointmentDateTime = appointment.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime now = LocalDateTime.now();
        long hoursDifference = ChronoUnit.HOURS.between(now, appointmentDateTime);
        if (hoursDifference <= 24) {
            model.addAttribute("appointment", appointment);
            model.addAttribute("errorMessage", 
                "Sorry, you cannot update or change an appointment that is within 24 hours of the scheduled time.");
            return "appointment-update-error";
        }

        // If we're here, it's okay to show the update form
        var appointmentDTO = new UpdateAppointmentRequestDTO();
        appointmentDTO.setAppointmentDate(appointment.getDate().toString());
        appointmentDTO.setDoctorId(appointment.getDoctor().getId());
        appointmentDTO.setId(appointment.getId());

        // Format the appointment date
        DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("EEEE, d MMMM yyyy");
        String formattedDate = appointmentDateTime.format(outputFormatter);

        model.addAttribute("appointmentDTO", appointmentDTO);
        model.addAttribute("allDoctors", doctorService.getAllDoctor());
        model.addAttribute("currentDoctorName", appointment.getDoctor().getName());
        model.addAttribute("formattedAppointmentDate", formattedDate);

        return "form-update-appointment";
    }

    @PostMapping("/appointment/update")
    public String updateAppointment(@ModelAttribute UpdateAppointmentRequestDTO appointmentDTO, Model model) {
        var appointmentFromDTO = new Appointment();
        appointmentFromDTO.setId(appointmentDTO.getId());
        appointmentFromDTO.setDoctor(doctorService.getDoctorById(appointmentDTO.getDoctorId()));

        // Check if the date is in ISO-8601 format or database timestamp format
        String dateString = appointmentDTO.getAppointmentDate();
        Date parsedDate;
        if (dateString.contains("T")) {
            // ISO-8601 format (new date selected)
            parsedDate = Date.from(Instant.parse(dateString));
        } else {
            // Database timestamp format (no new date selected)
            try {
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                parsedDate = dateFormat.parse(dateString);
            } catch (ParseException e) {
                throw new RuntimeException("Error parsing date: " + dateString, e);
            }
        }
        
        appointmentFromDTO.setDate(parsedDate);

        var appointment = appointmentService.updateAppointmentDoctorDate(appointmentFromDTO);

        model.addAttribute("responseMessage", 
            String.format("Appointment with ID %s has been successfully updated", appointment.getId()));

        return "success-update-appointment";
    }

    @GetMapping("/appointment/{id}/note")
    public String formUpdateDiagnosisTreatment(@PathVariable("id") String id, Model model) {
        var appointment = appointmentService.getAppointmentById(id);

        var appointmentDTO = new UpdateAppointmentRequestDTO();
        appointmentDTO.setId(appointment.getId());
        appointmentDTO.setDiagnosis(appointment.getDiagnosis());
        appointmentDTO.setTreatments(appointment.getTreatments());

        model.addAttribute("appointment", appointment);
        model.addAttribute("appointmentDTO", appointmentDTO);
        model.addAttribute("allTreatments", treatmentService.getAllTreatment());

        return "form-update-diagnosis-treatment";
    }

    @PostMapping("/appointment/note")
    public String updateDiagnosisTreatment(@ModelAttribute UpdateAppointmentRequestDTO appointmentDTO, Model model) {
        var appointmentFromDTO = new Appointment();
        appointmentFromDTO.setId(appointmentDTO.getId());
        appointmentFromDTO.setDiagnosis(appointmentDTO.getDiagnosis());
        appointmentFromDTO.setTreatments(appointmentDTO.getTreatments());

        var appointment = appointmentService.updateAppointmentDiagnosisTreatment(appointmentFromDTO);
        
        model.addAttribute("responseMessage",
            String.format("Appointment with ID %s's diagnosis and treatment has been successfully noted", appointment.getId()));
        
        return "success-update-diagnosis-treatment";
    }

    @PostMapping(value = "/appointment/note", params = {"addRow"})
    public String addRowTreatment(@ModelAttribute UpdateAppointmentRequestDTO appointmentDTO, Model model) {
        if (appointmentDTO.getTreatments() == null || appointmentDTO.getTreatments().isEmpty()) {
            appointmentDTO.setTreatments(new ArrayList<>());
        }

        appointmentDTO.getTreatments().add(new Treatment());
        model.addAttribute("appointment", appointmentService.getAppointmentById(appointmentDTO.getId()));
        model.addAttribute("appointmentDTO", appointmentDTO);
        model.addAttribute("allTreatments", treatmentService.getAllTreatment());
        return "form-update-diagnosis-treatment";
    }

    @PostMapping(value = "/appointment/note", params = {"deleteRow"})
    public String removeRowTreatment(@ModelAttribute UpdateAppointmentRequestDTO appointmentDTO, @RequestParam("deleteRow") int rowId, Model model) {
        appointmentDTO.getTreatments().remove(rowId);
        model.addAttribute("appointment", appointmentService.getAppointmentById(appointmentDTO.getId()));
        model.addAttribute("appointmentDTO", appointmentDTO);
        model.addAttribute("allTreatments", treatmentService.getAllTreatment());
        return "form-update-diagnosis-treatment";
    }

    @PostMapping("/appointment/{id}/done")
    public String statusDone(@PathVariable("id") String id, Model model) {
        var appointment = appointmentService.getAppointmentById(id);
        appointmentService.updateStatus(appointment, 1);
        model.addAttribute("appointment", appointment);
        return "detail-appointment";
    }

    @PostMapping("/appointment/{id}/cancel")
    public String statusCancel(@PathVariable("id") String id, Model model) {
        var appointment = appointmentService.getAppointmentById(id);
        appointmentService.updateStatus(appointment, 2);
        model.addAttribute("appointment", appointment);
        return "detail-appointment";
    }

}