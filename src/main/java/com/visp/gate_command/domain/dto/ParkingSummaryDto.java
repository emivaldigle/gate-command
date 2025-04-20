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
public class ParkingSummaryDto {
  private UUID entityId;
  private String identifier;
  private String currentLicensePlate;
  private Boolean available;
  private String lastUpdatedAt;
}
