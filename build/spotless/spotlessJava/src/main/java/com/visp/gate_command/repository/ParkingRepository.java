package com.visp.gate_command.repository;

import com.visp.gate_command.domain.entity.Parking;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, Long> {

  List<Parking> findAllByUserEntityId(Long entityId);

  List<Parking> findAllByUserId(Long userId);

  Optional<Parking> findByCurrentLicensePlate(String currentLicensePlate);
}
