package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Patient;

import java.util.List;
@Repository
public interface PatientRepository extends JpaRepository<Patient,Long> {
    List<Patient> findPatientsByHospitalId(Long id);

}
