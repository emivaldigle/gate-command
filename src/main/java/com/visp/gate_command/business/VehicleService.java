package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import java.util.List;
import java.util.Optional;

public interface VehicleService {
  VehicleDto create(VehicleDto vehicleDto);

  Optional<VehicleDto> update(VehicleDto vehicleDto);

  void delete(Long id);

  List<VehicleDto> getByUserId(Long userId);

  List<VehicleDto> getAllByEntity(Long entityId);

  List<VehicleSummaryDto> getAllSummariesByEntity(Long entityId);

  Optional<VehicleDto> findByLicensePlate(String licensePlate);
}
