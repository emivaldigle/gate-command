package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.ParkingDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ParkingService {
  ParkingDto create(ParkingDto parkingDto);

  Optional<ParkingDto> update(ParkingDto parkingDto);

  void delete(UUID parkingId);

  List<ParkingDto> getAllByEntity(UUID entityId);

  List<ParkingDto> getAllByUser(UUID userId);

  Optional<ParkingDto> findByCurrentLicensePlate(String currentLicensePlate);

  List<ParkingDto> findAllWithCreatedAtAndUpdatedAtGreaterThan(UUID entityId, String date);

  Optional<ParkingDto> findByEntityIdAndIdentifier(UUID entityId, String identifier);

  void seed(UUID entityId);
}
