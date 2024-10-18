package apap.ti.appointment2206082505.model;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.*;
import org.springframework.format.annotation.*;

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

    @ManyToMany
    @JoinTable(
        name = "appointment_treatment",
        joinColumns = @JoinColumn(name = "appointment_id"),
        inverseJoinColumns = @JoinColumn(name = "treatment_id")
    )
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
    
}
