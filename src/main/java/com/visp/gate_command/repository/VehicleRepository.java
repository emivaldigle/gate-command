package com.visp.gate_command.repository;

import com.visp.gate_command.domain.entity.Vehicle;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, UUID> {
  List<Vehicle> findByUserId(UUID userId);

  List<Vehicle> findByUserEntityId(UUID entityId);

  Optional<Vehicle> findByPlateAndUserEntityId(String plate, UUID entityId);
}
