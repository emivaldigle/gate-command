package com.visp.gate_command.domain.dto;

import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleSummaryDto {
  private UUID userId;
  private String plate;
  private boolean isVisit;
}
