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
@Table(name = "patient")
public class Patient {

    @Id
    private UUID id = UUID.randomUUID();

    @NotNull
    @Size(max = 100)
    @Column(name = "nik")
    private String nik;

    @NotNull
    @Size(max = 100)
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "gender")
    private Boolean gender;

    @Email
    @Size(max = 100)
    @Column(name = "email")
    private String email;

    @DateTimeFormat(pattern = "dd-MM-yyyy")
    @NotNull
    @Column(name = "birth_date")
    private Date birthDate;

    @Size(max = 100)
    @Column(name = "birth_place")
    private String birthPlace;

    @OneToMany(mappedBy = "patient")
    private List<Appointment> appointments;

    @CreationTimestamp
    @Column(name = "created_at")
    private Date createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private Date updatedAt;
}
