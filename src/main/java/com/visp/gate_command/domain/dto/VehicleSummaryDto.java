package com.visp.gate_command.domain.dto;

import lombok.Data;

@Data
public class VehicleSummaryDto {
  private String plate;
  private boolean isVisit;
}
