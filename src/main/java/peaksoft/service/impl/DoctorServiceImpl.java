package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Department;
import peaksoft.entity.Doctor;
import peaksoft.exceptions.MyException;
import peaksoft.repository.DepartmentRepository;
import peaksoft.service.DepartmentService;
import peaksoft.service.DoctorService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import peaksoft.repository.DoctorRepository;
import peaksoft.service.HospitalService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorServiceImpl implements DoctorService {
private final DepartmentRepository departmentRepository;
    private final DoctorRepository doctorRepository;
    private final DepartmentService departmentService;
    private final HospitalService hospitalService;


    @Override
    public void saveDoctor(Doctor doctor, Long departmentId) throws MyException {
        try {
            Department department = departmentService.getDepartmentById(departmentId);
            doctor.setDepartments((List<Department>) department);
            doctorRepository.save(doctor);
            List<Doctor> departmentDoctors = department.getDoctors();
            departmentDoctors.add(doctor);
            departmentRepository.save(department);
        } catch (Exception e) {
            throw new MyException("Error while saving doctor", e);
        }
    }



    @Override
    public Doctor getDoctorById(Long id) throws MyException {
        Optional<Doctor> doctor = doctorRepository.findById(id);
        return doctor.orElseThrow(() -> new MyException("Doctor not found"));
    }

    @Override
    public List<Doctor> getAllDoctors() throws MyException {
        try {
            return doctorRepository.findAll();
        } catch (Exception e) {
            throw new MyException("Error while getting doctors", e);
        }
    }

    @Override
    public void updateDoctor(Long id, Doctor updatedDoctor) throws MyException {
        if (doctorRepository.existsById(id)) {
            updatedDoctor.setId(id);
            doctorRepository.save(updatedDoctor);
        } else {
            throw new MyException("Doctor not found");
        }
    }

    @Override
    public void deleteDoctor(Long id) throws MyException {
        try {
            doctorRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("Error while deleting doctor", e);
        }
    }

    @Override
    public List<Doctor> findAll(Long hospitalId) throws MyException {
        try {
            hospitalService.getHospitalById(hospitalId);

            return doctorRepository.findDoctorByHospitalId(hospitalId);
        } catch (Exception e) {
            throw new MyException("Error while getting doctors by hospital", e);
        }
    }

}



