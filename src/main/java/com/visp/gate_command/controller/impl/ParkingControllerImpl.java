package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.controller.ParkingController;
import com.visp.gate_command.domain.dto.ParkingDto;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class ParkingControllerImpl implements ParkingController {

  private final ParkingService parkingService;

  @Override
  public ResponseEntity<ParkingDto> create(ParkingDto parkingDto) {
    return ResponseEntity.ok(parkingService.create(parkingDto));
  }

  @Override
  public ResponseEntity<ParkingDto> update(UUID id, ParkingDto parkingDto) {
    parkingDto.setId(id);
    return ResponseEntity.ok(
        parkingService
            .update(parkingDto)
            .orElseThrow(() -> new RuntimeException("Parking not found")));
  }

  @Override
  public ResponseEntity<List<ParkingDto>> getAllByEntity(UUID entityId) {
    return ResponseEntity.ok(parkingService.getAllByEntity(entityId));
  }

  @Override
  public ResponseEntity<List<ParkingDto>> findByEntityAndGreaterThanDate(
      UUID entityId, String date) {
    return ResponseEntity.ok(
        parkingService.findAllWithCreatedAtAndUpdatedAtGreaterThan(entityId, date));
  }

  @Override
  public ResponseEntity<List<ParkingDto>> getAllByUser(UUID userId) {
    return ResponseEntity.ok(parkingService.getAllByUser(userId));
  }

  @Override
  public ResponseEntity<Void> seed(UUID entityId) {
    parkingService.seed(entityId);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<Void> delete(UUID id) {
    parkingService.delete(id);
    return ResponseEntity.accepted().build();
  }
}
