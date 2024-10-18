package apap.ti.appointment2206082505.model;

import lombok.*;
import java.util.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import org.hibernate.annotations.*;

import jakarta.persistence.Table;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "doctor")
public class Doctor {
    
    @Id
    private String id;

    @NotNull
    @Size(max = 255)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "specialist")
    private Integer specialist;

    @Email
    @Size(max = 100)
    @Column(name = "email")
    private String email;
    
    @NotNull
    @Column(name = "gender")
    private Boolean gender;

    @NotNull
    @Min(0)
    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    @ElementCollection
    @Column(name = "schedule")
    private List<Integer> schedule;

    @NotNull
    @Column(name = "fee")
    private Long fee;

    @OneToMany(mappedBy = "doctor")
    private List<Appointment> appointments;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
