package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Appointment;
import peaksoft.entity.Hospital;
import peaksoft.exceptions.MyException;
import peaksoft.repository.AppointmentRepository;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.AppointmentService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class AppointmentServiceImpl implements AppointmentService {
    private final AppointmentRepository appointmentRepository;
    private final HospitalServiceImpl hospitalService;
private final HospitalRepository hospitalRepository;

@Override
    public void saveAppointment(Appointment appointment, Long hospitalId) throws MyException {
        Hospital hospital = hospitalRepository.findById(hospitalId).orElse(null); // Замените на ваш репозиторий для Hospital
        if (hospital == null) {
            throw new MyException("Hospital not found with ID: " + hospitalId);
        }

        List<Appointment> hospitalAppointments = hospital.getAppointments();
        hospitalAppointments.add(appointment);
        hospitalRepository.save(hospital);

        try {
            appointmentRepository.save(appointment);
        } catch (Exception e) {
            throw new MyException("Error while saving appointment");
        }
    }


    @Override
    public Appointment getAppointmentById(Long id) throws MyException {
        Optional<Appointment> appointment = appointmentRepository.findById(id);
        return appointment.orElseThrow(() -> new MyException("Appointment not found"));
    }

    @Override
    public List<Appointment> getAllAppointments() throws MyException {
        try {
            return appointmentRepository.findAll();
        } catch (Exception e) {
            throw new MyException("Error while getting appointments", e);
        }
    }

    @Override
    public void updateAppointment(Long id, Appointment updatedAppointment) throws MyException {
        if (appointmentRepository.existsById(id)) {
            updatedAppointment.setId(id); // Assuming your Appointment class has a setId() method
            appointmentRepository.save(updatedAppointment);
        } else {
            throw new MyException("Appointment not found");
        }
    }

    @Override
    public void deleteAppointment(Long id) throws MyException {
        try {
            appointmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("Error while deleting appointment", e);
        }
    }

    @Override
    public List<Appointment> findAll(Long hospitalId) throws MyException {
        try {
            hospitalService.getHospitalById(hospitalId);
            return appointmentRepository.findAppointmentsByHospitalId(hospitalId);
        } catch (Exception e) {
            throw new MyException("It is error while getting appointment by hospital id");
        }
    }

}
