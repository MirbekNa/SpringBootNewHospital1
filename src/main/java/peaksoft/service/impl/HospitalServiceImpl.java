package peaksoft.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import peaksoft.entity.Hospital;
import peaksoft.exceptions.MyException;
import peaksoft.repository.HospitalRepository;
import peaksoft.service.DepartmentService;
import peaksoft.service.HospitalService;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
@RequiredArgsConstructor
public class HospitalServiceImpl implements HospitalService {

    private final HospitalRepository hospitalRepository;


    @Override
    public void saveHospital(Hospital hospital) throws MyException {
        try {
            hospitalRepository.save(hospital);
        } catch (Exception e) {
            throw new MyException("Error while saving hospital", e);
        }
    }

    @Override
    public Hospital getHospitalById(Long id) throws MyException {
        Optional<Hospital> hospital = hospitalRepository.findById(id);
        return hospital.orElseThrow(() -> new MyException("Hospital not found", new Exception()));
    }

    @Override
    public List<Hospital> getAllHospitals() throws MyException {
        try {
            return hospitalRepository.findAll();
        } catch (Exception e) {
            throw new MyException("Error while getting hospitals", e);
        }
    }

    @Override
    public void updateHospital(Long id, Hospital updatedHospital) throws MyException {
        if (hospitalRepository.existsById(id)) {
            updatedHospital.setId(id);
            hospitalRepository.save(updatedHospital);
        } else {
            throw new MyException("Hospital not found", new Exception());
        }
    }

    @Override
    public void deleteHospital(Long id) throws MyException {
        try {
            hospitalRepository.deleteById(id);
        } catch (Exception e) {
            throw new MyException("Error while deleting hospital", e);
        }
    }
}
