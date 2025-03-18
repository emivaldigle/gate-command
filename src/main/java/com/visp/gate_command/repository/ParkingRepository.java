package com.visp.gate_command.repository;

import com.visp.gate_command.domain.entity.Parking;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ParkingRepository extends JpaRepository<Parking, UUID> {

  List<Parking> findAllByEntityId(UUID entityId);

  List<Parking> findAllByUserId(UUID userId);

  Optional<Parking> findByCurrentLicensePlate(String currentLicensePlate);

  Optional<Parking> findByEntityIdAndIdentifier(UUID entityId, String identifier);

}
