package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Hospital;
import peaksoft.entity.Patient;

import java.util.List;

@Repository
public interface HospitalRepository extends JpaRepository<Hospital,Long> {


}
