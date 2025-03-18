package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.VehicleService;
import com.visp.gate_command.controller.VehicleController;
import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class VehicleControllerImpl implements VehicleController {

  private final VehicleService vehicleService;

  @Override
  public ResponseEntity<VehicleDto> create(VehicleDto vehicleDto) {
    return ResponseEntity.ok(vehicleService.create(vehicleDto));
  }

  @Override
  public ResponseEntity<VehicleDto> update(UUID id, VehicleDto vehicleDto) {
    vehicleDto.setId(id);
    return ResponseEntity.ok(
        vehicleService
            .update(vehicleDto)
            .orElseThrow(() -> new RuntimeException("Vehicle not found")));
  }

  @Override
  public ResponseEntity<Void> delete(UUID id, UUID entityId) {
    vehicleService.delete(id, entityId);
    return ResponseEntity.noContent().build();
  }

  @Override
  public ResponseEntity<List<VehicleDto>> getByUserId(UUID userId) {
    return ResponseEntity.ok(vehicleService.getByUserId(userId));
  }

  @Override
  public ResponseEntity<List<VehicleDto>> getAllByEntity(UUID entityId) {
    return ResponseEntity.ok(vehicleService.getAllByEntity(entityId));
  }

  @Override
  public ResponseEntity<List<VehicleSummaryDto>> getAllSummariesByEntity(UUID entityId) {
    return ResponseEntity.ok(vehicleService.getAllSummariesByEntity(entityId));
  }

  @Override
  public ResponseEntity<VehicleDto> findByPlateAndEntityId(String licensePlate, UUID entityId) {
    return ResponseEntity.ok(
        vehicleService
            .findByLicensePlateAndEntityId(licensePlate, entityId)
            .orElseThrow(() -> new RuntimeException("Vehicle not found")));
  }
}
