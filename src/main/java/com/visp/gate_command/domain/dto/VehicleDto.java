package com.visp.gate_command.domain.dto;

import com.visp.gate_command.domain.enums.VehicleType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

  private Long id;

  private UserDto user;

  private String plate;

  private LocalDateTime createdAt;

  private VehicleType vehicleType;
}
