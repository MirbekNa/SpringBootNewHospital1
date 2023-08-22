package peaksoft.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "hospitals")
public class Hospital {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hospital_gen")
    @SequenceGenerator(name = "hospital_gen", sequenceName = "hospital_seq", allocationSize = 1)
    private Long id;

    @NotNull(message = "Hospital name can't be empty")
    private String name;

    @NotNull(message = "Hospital address can't be empty")
    private String address;

    @Column(length = 10000)
    private String image;

    @PrePersist
    public void initializeImage() {
        if (image == null || image.isEmpty()) {
            image = "https://www.example.com/default-image.png"; // Default image URL
        }
    }

    @NotNull(message = "Departments list can't be empty")
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Department> departments;

    @NotNull(message = "Appointments list can't be empty")
    @OneToMany(cascade = CascadeType.ALL)
    private List<Appointment> appointments;

    @NotNull(message = "Patients list can't be empty")
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Patient> patients;

    @NotNull(message = "Doctors list can't be empty")
    @OneToMany(mappedBy = "hospital", cascade = CascadeType.ALL)
    private List<Doctor> doctors;

    public void addDepartment(Department department) {
        if (departments == null) {
            departments = new ArrayList<>();
        }
        departments.add(department);
    }

    public void addDoctor(Doctor doctor) {
        if (doctors == null) {
            doctors = new ArrayList<>();
        }
        doctors.add(doctor);
    }

    public void addPatient(Patient patient) {
        if (patients == null) {
            patients = new ArrayList<>();
        }
        patients.add(patient);
    }

    public void addAppointment(Appointment appointment) {
        if (appointments == null) {
            appointments = new ArrayList<>();
        }
        appointments.add(appointment);
    }
}
