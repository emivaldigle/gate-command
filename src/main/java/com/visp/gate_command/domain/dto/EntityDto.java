package com.visp.gate_command.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

  @NotEmpty
  @Size(min = 2, max = 20)
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

  private int visitSizeLimit;

  private int parkingSizeLimit;

  private boolean active;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime lastUpdatedAt;
}
