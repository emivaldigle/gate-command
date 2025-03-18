package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface VehicleService {
  VehicleDto create(VehicleDto vehicleDto);

  Optional<VehicleDto> update(VehicleDto vehicleDto);

  void delete(UUID userId, UUID entityId);

  List<VehicleDto> getByUserId(UUID userId);

  List<VehicleDto> getAllByEntity(UUID entityId);

  List<VehicleSummaryDto> getAllSummariesByEntity(UUID entityId);

  Optional<VehicleDto> findByLicensePlateAndEntityId(String licensePlate, UUID entityId);
}
