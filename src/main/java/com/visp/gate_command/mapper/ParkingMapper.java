package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.entity.Parking;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ParkingMapper {
  @Mapping(source = "entityId", target = "entity.id")
  Parking toEntity(ParkingDto parkingDto);

  @Mapping(source = "entity.id", target = "entityId")
  ParkingDto toDto(Parking parking);
}
