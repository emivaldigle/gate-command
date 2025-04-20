package com.visp.gate_command.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityConfigurationDto {
  private int syncIntervalMinutes;

  private int parkingHoursAllowed;

  private int visitSizeLimit;

  private int parkingSizeLimit;

  private boolean active;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime lastUpdatedAt;

  private List<PocDto> pocList;
}
