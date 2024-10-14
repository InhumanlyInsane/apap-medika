package apap.ti.appointment2206082505.model;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.*;
import java.text.SimpleDateFormat;

import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "appointment")
public class Appointment {
    
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "doctor_id", nullable = false)
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    @Column(name = "date")
    private Date date;

    @Size(max = 255)
    @Column(name = "diagnosis")
    private String diagnosis;

    @OneToMany
    private List<Treatment> treatments;

    @NotNull
    @Column(name = "total_fee")
    private Long totalFee;

    @NotNull
    @Column(name = "status")
    private Integer status;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;

    private static int appointmentCounter = 0;
    private static final Map<Integer, String> specializationMap = new HashMap<Integer, String>() {{
        put(0, "UMM");  // Dokter umum
        put(1, "GGI");  // Dokter gigi
        put(2, "ANK");  // Spesialis Anak
        put(3, "BDH");  // Bedah
        put(4, "PRE");  // Bedah Plastik, Rekonstruksi, dan Estetik
        put(5, "JPD");  // Jantung dan Pembuluh Darah
        put(6, "KKL");  // Kulit dan Kelamin
        put(7, "MTA");  // Mata
        put(8, "OBG");  // Obstetri dan Ginekologi
        put(9, "PDL");  // Penyakit Dalam
        put(10, "PRU"); // Paru
        put(11, "THT"); // Telinga, Hidung, Tenggorokan, Bedah Kepala Leher
        put(12, "RAD"); // Radiologi
        put(13, "KSJ"); // Kesehatan Jiwa
        put(14, "ANS"); // Anestesi
        put(15, "NRO"); // Neurologi
        put(16, "URO"); // Urologi
    }};

    @PrePersist
    public void prePersist() {
        if (this.id == null) {
            this.id = generateAppointmentId();
        }
    }

    private String generateAppointmentId() {
        String specializationCode = specializationMap.getOrDefault(doctor.getSpecialist(), "UNK");
        String datePart = new SimpleDateFormat("ddMM").format(date);
        String counterPart = String.format("%03d", ++appointmentCounter);

        return specializationCode + datePart + counterPart;
    }
    
}
