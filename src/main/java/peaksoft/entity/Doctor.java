package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import peaksoft.enums.DoctorType;
import peaksoft.enums.Gender;

import java.util.List;

@Data
@Entity
@Table(name = "doctors")
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "doctor_gen")
    @SequenceGenerator(name = "doctor_gen", sequenceName = "doctor_seq", allocationSize = 1)
    private Long id; @Size(max = 50, message = "Last name must not exceed 50 characters")
    @NotNull(message = "Field can't be empty")
    private String firstName; @Size(max = 50, message = "Last name must not exceed 50 characters")
    @NotNull(message = "Field can't be empty")
    private String lastName;
    @NotNull(message = "Field can't be empty")
    @Enumerated(EnumType.STRING)
    private Gender gender;
    @NotNull(message = "Field can't be empty")
    @Enumerated(EnumType.STRING)
    private DoctorType position;

    @Size(max = 100, message = "Email must not exceed 100 characters")
    @NotNull(message = "Field can't be empty")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Field can't be empty")
    @ManyToMany(mappedBy = "doctors", cascade = {CascadeType.DETACH,
    CascadeType.MERGE,
    CascadeType.REFRESH,
    CascadeType.PERSIST})
    private List<Department> departments;
    @NotNull(message = "Field can't be empty")
    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL)
    private List<Appointment> appointments;
    @NotNull(message = "Field can't be empty")
    @ManyToOne(cascade = {CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH,
            CascadeType.PERSIST})
    private  Hospital hospital;
}
