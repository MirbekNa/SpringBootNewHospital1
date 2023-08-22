package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Department;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
   List<Department>findDepartmentByHospitalId(Long id);

}
