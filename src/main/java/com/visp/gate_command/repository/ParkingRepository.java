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

  List<Parking> findAllByEntityIdAndCreatedAtGreaterThanOrLastUpdatedAtGreaterThan(
      UUID entityId, LocalDateTime dateTime1, LocalDateTime dateTime2);

  @Modifying
  @Query(
      value =
          "UPDATE parking SET "
              + "current_license_plate = :currentLicensePlate, "
              + "available = :available, "
              + "last_updated_at = :lastUpdatedAt "
              + "WHERE entity_id = :entityId "
              + "AND identifier = :identifier",
      nativeQuery = true)
  int updateParking(
      @Param("currentLicensePlate") String currentLicensePlate,
      @Param("available") Boolean available,
      @Param("lastUpdatedAt") LocalDateTime lastUpdatedAt,
      @Param("entityId") UUID entityId,
      @Param("identifier") String identifier);
}
