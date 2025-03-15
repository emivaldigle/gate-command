package com.visp.gate_command.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleSummaryDto {
  private Long userId;
  private String plate;
  private boolean isVisit;
}
