package apap.ti.appointment2206082505;

import java.util.*;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import apap.ti.appointment2206082505.model.Appointment;
import apap.ti.appointment2206082505.model.Doctor;
import apap.ti.appointment2206082505.model.Patient;
import apap.ti.appointment2206082505.service.AppointmentService;
import apap.ti.appointment2206082505.service.DoctorService;
import apap.ti.appointment2206082505.service.PatientService;
import jakarta.transaction.Transactional;

@SpringBootApplication
public class Appointment2206082505Application {

	public static void main(String[] args) {
		SpringApplication.run(Appointment2206082505Application.class, args);
	}

	@Bean
	@Transactional
	CommandLineRunner run(AppointmentService appointmentService, DoctorService doctorService, PatientService patientService) {
		return args -> {
			@SuppressWarnings("deprecation")
			var faker = new Faker(new Locale("in-ID"));

			var appointment = new Appointment();
			var fakeAppointment = faker.leagueOfLegends();
			var fakeDate = faker.date();

			appointment.setDate(fakeDate.between(fakeDate.past(10, TimeUnit.DAYS), fakeDate.future(10, TimeUnit.DAYS)));
			appointment.setDiagnosis(fakeAppointment.quote());
			appointment.setTotalFee(1000L);
			appointment.setStatus(0);
			appointment.setCreatedAt(fakeDate.past(5, TimeUnit.DAYS));
			appointment.setUpdatedAt(fakeDate.past(1, TimeUnit.DAYS));

			var doctor = new Doctor();
			doctor.setName(fakeAppointment.champion());
			doctor.setSpecialist(faker.number().numberBetween(0, 13));
			doctor.setEmail("fakedoctor@test.com");
			doctor.setGender(faker.bool().bool());
			doctor.setYearsOfExperience(faker.number().numberBetween(0, 50));
			doctor.setSchedule(List.of(1, 2, 3, 4, 5));
			doctor.setFee(1000L);
			doctor.setCreatedAt(fakeDate.past(5, TimeUnit.DAYS));
			doctor.setUpdatedAt(fakeDate.past(1, TimeUnit.DAYS));
			var newDoctor = doctorService.addDoctor(doctor);
			appointment.setDoctor(newDoctor);

			var patient = new Patient();
			patient.setNik(faker.idNumber().valid());
			patient.setName(fakeAppointment.champion());
			patient.setGender(faker.bool().bool());
			patient.setEmail("fakepatient@test.com");
			patient.setBirthDate(fakeDate.birthday());
			patient.setBirthPlace(faker.address().city());
			patient.setCreatedAt(fakeDate.past(5, TimeUnit.DAYS));
			patient.setUpdatedAt(fakeDate.past(1, TimeUnit.DAYS));
			var newPatient = patientService.addPatient(patient);
			appointment.setPatient(newPatient);

			appointmentService.addAppointment(appointment);

		};
	}

}
