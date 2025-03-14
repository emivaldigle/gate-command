package com.visp.gate_command.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityDto {

  private Long id;

  private String name;

  private String type;

  private String address;

  private String region;

  private String city;

  private String commune;

  private String taxId;

  private String contactPhone;

  private int syncIntervalMinutes;

  private int parkingHoursAllowed;
  private boolean active;
  private LocalDateTime createdAt;
  private LocalDateTime lastUpdatedAt;
}
