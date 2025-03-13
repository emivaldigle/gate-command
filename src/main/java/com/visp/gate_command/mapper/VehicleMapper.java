package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.entity.Vehicle;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

  Vehicle toEntity(VehicleDto vehicleDto);

  VehicleDto toDto(Vehicle vehicle);
}
