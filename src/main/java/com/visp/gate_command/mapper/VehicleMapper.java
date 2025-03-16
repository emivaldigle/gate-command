package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import com.visp.gate_command.domain.entity.Vehicle;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface VehicleMapper {

  Vehicle toEntity(VehicleDto vehicleDto);

  VehicleDto toDto(Vehicle vehicle);

  @Mapping(
      target = "isVisit",
      expression =
          "java(vehicle.getUser().getType() == com.visp.gate_command.domain.enums.UserType.VISIT)")
  @Mapping(target = "userId", source = "user.id")
  VehicleSummaryDto toVehicleSummaryDto(Vehicle vehicle);
}
