package com.visp.gate_command.repository;

import com.visp.gate_command.domain.entity.Vehicle;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {
  List<Vehicle> findByUserId(Long userId);

  List<Vehicle> findByUserEntityId(Long entityId);
}
