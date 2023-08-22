package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import peaksoft.entity.Department;
import peaksoft.entity.Hospital;
import peaksoft.exceptions.MyException;
import peaksoft.repository.DepartmentRepository;

import peaksoft.service.DepartmentService;
import peaksoft.service.HospitalService;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class DepartmentServiceImpl implements DepartmentService {
    private final DepartmentRepository departmentRepository;
    private final HospitalService hospitalService;

    @Override
    public void saveDepartment(Department department, Long hospitalId) throws MyException {
        try {
            Hospital hospital = hospitalService.getHospitalById(hospitalId);
            department.setHospital(hospital);
            departmentRepository.save(department);
        } catch (Exception e) {
            throw new MyException("Error while saving department", e);
        }
    }

    @Override
    public Department getDepartmentById(Long id) throws MyException {
        Optional<Department> department = departmentRepository.findById(id);
        return department.orElseThrow(() -> new MyException("Department not found"));
    }

    @Override
    public List<Department> getAllDepartments() throws MyException {
        try {
            return departmentRepository.findAll();
        } catch (Exception e) {
            throw new MyException("Error while getting departments", e);
        }
    }

    @Override
    public void updateDepartment(Long id, Department updatedDepartment) throws MyException {
        if (departmentRepository.existsById(id)) {
            updatedDepartment.setId(id);
            departmentRepository.save(updatedDepartment);
        } else {
            throw new MyException("Department not found");
        }
    }

    @Override
    public void deleteDepartment(Long id) throws MyException {
        try {
            departmentRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("Error while deleting department", e);
        }
    }

    @Override
    public List<Department> findAll(Long hospitalId) throws MyException {
        try {
            hospitalService.getHospitalById(hospitalId);
            return departmentRepository.findDepartmentByHospitalId(hospitalId);
        } catch (Exception e) {
            throw new MyException("It is error while getting department by hospital id");
        }
    }

}
