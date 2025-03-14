package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.entity.Parking;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParkingMapper {
  Parking toEntity(ParkingDto parkingDto);

  ParkingDto toDto(Parking parking);
}
