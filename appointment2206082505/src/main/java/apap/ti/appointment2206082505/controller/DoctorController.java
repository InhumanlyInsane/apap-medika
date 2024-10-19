package apap.ti.appointment2206082505.controller;

import java.util.stream.Collectors;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import apap.ti.appointment2206082505.dto.request.AddDoctorRequestDTO;
import apap.ti.appointment2206082505.dto.request.UpdateAppointmentRequestDTO;
import apap.ti.appointment2206082505.dto.request.UpdateDoctorRequestDTO;
import apap.ti.appointment2206082505.model.Doctor;
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

    @GetMapping("/doctor/create")
    public String formCreateDoctor(Model model) {
        var doctorDTO = new AddDoctorRequestDTO();
        doctorDTO.setSchedule(new ArrayList<>());
        var specializationList = doctorService.getSpecializationMap().values();
        model.addAttribute("specializationList", specializationList);
        model.addAttribute("doctorDTO", doctorDTO);
        return "form-create-doctor";
    }

    @PostMapping("/doctor/create")
    public String createDoctor(@ModelAttribute AddDoctorRequestDTO doctorDTO, Model model) {
        var doctor = new Doctor();
        
        doctor.setName(doctorDTO.getName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setGender(doctorDTO.getGender());
        doctor.setSpecialist(mapSpecializationToInteger(doctorDTO.getSpecialization()));
        doctor.setYearsOfExperience(doctorDTO.getYearsOfExperience());
        List<Integer> scheduleIntegers = doctorDTO.getSchedule().stream()
            .map(this::mapDayToInteger)
            .collect(Collectors.toList());
        doctor.setSchedule(scheduleIntegers);
        doctor.setFee(doctorDTO.getFee());
        doctor.setCreatedAt(new Date());

        doctorService.addDoctor(doctor);

        model.addAttribute("responseMessage", 
            String.format("Doctor with ID %s has been added", doctor.getId()));

        return "success-create-doctor";
    }

    @PostMapping(value = "/doctor/create", params = {"addRow"})
    public String addRowScheduleCreate(@ModelAttribute AddDoctorRequestDTO doctorDTO, Model model) {
        if (doctorDTO.getSchedule() == null) {
            doctorDTO.setSchedule(new ArrayList<>());
        }

        doctorDTO.getSchedule().add("");
        var specializationList = doctorService.getSpecializationMap().values();
        model.addAttribute("specializationList", specializationList);
        model.addAttribute("doctorDTO", doctorDTO);
        return "form-create-doctor";
    }

    @PostMapping(value = "/doctor/create", params = {"deleteRow"})
    public String deleteRowScheduleCreate(@ModelAttribute AddDoctorRequestDTO doctorDTO, @RequestParam("deleteRow") int rowId, Model model) {
        if (doctorDTO.getSchedule() == null) {
            doctorDTO.setSchedule(new ArrayList<>());
        }

        if (doctorDTO.getSchedule().size() > 1) {
            doctorDTO.getSchedule().remove(rowId);
        }
        var specializationList = doctorService.getSpecializationMap().values();
        model.addAttribute("specializationList", specializationList);
        model.addAttribute("doctorDTO", doctorDTO);
        return "form-create-doctor";
    }

    @GetMapping("/doctor/{id}/update")
    public String formUpdateDoctor(@PathVariable("id") String id, Model model) {
        var doctor = doctorService.getDoctorById(id);
        
        var doctorDTO = new UpdateDoctorRequestDTO();
        doctorDTO.setId(doctor.getId());
        doctorDTO.setName(doctor.getName());
        doctorDTO.setEmail(doctor.getEmail());
        doctorDTO.setGender(doctor.getGender());
        doctorDTO.setSpecialization(doctorService.getSpecializationMap().get(doctor.getSpecialist()));
        doctorDTO.setYearsOfExperience(doctor.getYearsOfExperience());
        doctorDTO.setFee(doctor.getFee());

        List<String> schedule = new ArrayList<>();
        for (int scheduleInt : doctor.getSchedule()) {
            String dayName = mapIntegerToDay(scheduleInt);
            schedule.add(dayName);
        }
        doctorDTO.setSchedule(schedule);

        var specializationList = doctorService.getSpecializationMap().values();

        model.addAttribute("doctorDTO", doctorDTO);
        model.addAttribute("specializationList", specializationList);

        return "form-update-doctor";
    }

    @PostMapping("/doctor/update")
    public String updateDoctor(@ModelAttribute UpdateDoctorRequestDTO doctorDTO, Model model) {
        Doctor doctor = doctorService.getDoctorById(doctorDTO.getId());
        doctor.setName(doctorDTO.getName());
        doctor.setEmail(doctorDTO.getEmail());
        doctor.setGender(doctorDTO.getGender());
        doctor.setSpecialist(mapSpecializationToInteger(doctorDTO.getSpecialization()));
        doctor.setYearsOfExperience(doctorDTO.getYearsOfExperience());
        doctor.setFee(doctorDTO.getFee());

        List<Integer> scheduleIntegers = doctorDTO.getSchedule().stream()
                .map(this::mapDayToInteger)
                .collect(Collectors.toList());
        doctor.setSchedule(scheduleIntegers);

        doctorService.updateDoctor(doctor);

        model.addAttribute("responseMessage", 
            String.format("Doctor with ID %s has been updated", doctor.getId()));
        
        return "success-update-doctor";
    }

    @PostMapping(value = "/doctor/update", params = {"addRow"})
    public String addRowScheduleUpdate(@ModelAttribute UpdateDoctorRequestDTO doctorDTO, Model model) {
        if (doctorDTO.getSchedule() == null) {
            doctorDTO.setSchedule(new ArrayList<>());
        }

        doctorDTO.getSchedule().add("");
        var specializationList = doctorService.getSpecializationMap().values();
        model.addAttribute("specializationList", specializationList);
        model.addAttribute("doctorDTO", doctorDTO);
        return "form-update-doctor";
    }

    @PostMapping(value = "/doctor/update", params = {"deleteRow"})
    public String deleteRowScheduleUpdate(@ModelAttribute UpdateDoctorRequestDTO doctorDTO, @RequestParam("deleteRow") int rowId, Model model) {
        if (doctorDTO.getSchedule() == null) {
            doctorDTO.setSchedule(new ArrayList<>());
        }

        if (doctorDTO.getSchedule().size() > 1) {
            doctorDTO.getSchedule().remove(rowId);
        }
        var specializationList = doctorService.getSpecializationMap().values();
        model.addAttribute("specializationList", specializationList);
        model.addAttribute("doctorDTO", doctorDTO);
        return "form-update-doctor";
    }

    private Integer mapDayToInteger(String day) {
        return switch (day.trim().toLowerCase()) {
            case "monday" -> 1;
            case "tuesday" -> 2;
            case "wednesday" -> 3;
            case "thursday" -> 4;
            case "friday" -> 5;
            case "saturday" -> 6;
            case "sunday" -> 7;
            default -> throw new IllegalArgumentException("Invalid day: " + day);
        };
    }

    private String mapIntegerToDay(int dayNumber) {
        return switch (dayNumber) {
            case 1 -> "monday";
            case 2 -> "tuesday";
            case 3 -> "wednesday";
            case 4 -> "thursday";
            case 5 -> "friday";
            case 6 -> "saturday";
            case 7 -> "sunday";
            default -> throw new IllegalArgumentException("Invalid day number: " + dayNumber);
        };
    }

    private Integer mapSpecializationToInteger(String specialization) {
        for (var entry : doctorService.getSpecializationMap().entrySet()) {
            if (entry.getValue().equalsIgnoreCase(specialization)) {
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Invalid specialization: " + specialization);
    }

}
