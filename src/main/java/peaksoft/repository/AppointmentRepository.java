package peaksoft.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import peaksoft.entity.Appointment;

import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment,Long> {
    @Query("select h from Hospital l " +
            "join l.appointments h where l.id=:id " +
            "order by h.id desc")
    List<Appointment> findAppointmentByHospitalId(Long id);

}

