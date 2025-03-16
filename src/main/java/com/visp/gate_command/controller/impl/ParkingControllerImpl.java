package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.controller.ParkingController;
import com.visp.gate_command.domain.dto.ParkingDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class ParkingControllerImpl implements ParkingController {

  private final ParkingService visitService;

  @Override
  public ResponseEntity<ParkingDto> create(ParkingDto parkingDto) {
    return ResponseEntity.ok(visitService.create(parkingDto));
  }

  @Override
  public ResponseEntity<ParkingDto> update(Long id, ParkingDto parkingDto) {
    parkingDto.setId(id);
    return ResponseEntity.ok(
        visitService
            .update(parkingDto)
            .orElseThrow(() -> new RuntimeException("Parking not found")));
  }

  @Override
  public ResponseEntity<List<ParkingDto>> getAllByEntity(Long entityId) {
    return ResponseEntity.ok(visitService.getAllByEntity(entityId));
  }

  @Override
  public ResponseEntity<List<ParkingDto>> getAllByUser(Long userId) {
    return ResponseEntity.ok(visitService.getAllByUser(userId));
  }

  @Override
  public ResponseEntity<Void> seed(Long entityId) {
    visitService.seed(entityId);
    return ResponseEntity.accepted().build();
  }
}
