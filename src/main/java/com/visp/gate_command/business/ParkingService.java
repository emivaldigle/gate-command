package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.ParkingDto;
import java.util.List;
import java.util.Optional;

public interface ParkingService {
  ParkingDto create(ParkingDto parkingDto);

  Optional<ParkingDto> update(ParkingDto parkingDto);

  List<ParkingDto> getAllByEntity(Long entityId);

  List<ParkingDto> getAllByUser(Long userId);
}
