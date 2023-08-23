package peaksoft.service.impl;

import jakarta.transaction.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Department;
import peaksoft.entity.Hospital;
import peaksoft.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;

import peaksoft.entity.Patient;
import peaksoft.exceptions.MyException;
import peaksoft.repository.PatientRepository;
import peaksoft.service.HospitalService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientRepository patientRepository;
    private final HospitalService hospitalService;



    @Override
    public void savePatient(Patient patient, Long hospitalId) throws MyException {
        try {
            Hospital hospital = hospitalService.getHospitalById(hospitalId);
            patient.setHospital((Hospital) hospital);

            patientRepository.save(patient);
        } catch (Exception e) {
            throw new MyException("Error while saving patient", e);
        }
    }

    @Override
    public Patient getPatientById(Long id) throws MyException {
        Optional<Patient> patient = patientRepository.findById(id);
        return patient.orElseThrow(() -> new MyException("Patient not found"));
    }

    @Override
    public List<Patient> getAllPatients() throws MyException {
        try {
            return patientRepository.findAll();
        } catch (Exception e) {
            throw new MyException("Error while getting patients", e);
        }
    }

    @Override
    public void updatePatient(Long id, Patient updatedPatient) throws MyException {
        if (patientRepository.existsById(id)) {
            updatedPatient.setId(id);
            patientRepository.save(updatedPatient);
        } else {
            throw new MyException("Patient not found",new Exception());
        }
    }

    @Override
    public void deletePatient(Long id) throws MyException {
        try {
            patientRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("Error while deleting patient", e);
        }
    }

    @Override
    public List<Patient> findAll(Long hospitalId) throws MyException {
        try {
            hospitalService.getHospitalById(hospitalId);
            return patientRepository.findPatientsByHospitalId(hospitalId);
        } catch (Exception e) {
            throw new MyException("Error while getting patients by hospital", e);
        }
    }

}
