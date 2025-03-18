package com.visp.gate_command.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visp.gate_command.domain.enums.EntityType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EntityDto {

  private UUID id;

  @NotEmpty
  @Size(min = 2, max = 50)
  private String name;

  private EntityType type;

  @NotEmpty
  @Size(max = 100)
  private String address;

  @NotEmpty
  @Size(max = 50)
  private String region;

  @NotEmpty
  @Size(max = 100)
  private String city;

  @NotEmpty
  @Size(max = 100)
  private String commune;

  @NotEmpty
  @Size(max = 30)
  private String taxId;

  @NotEmpty
  @Size(max = 15)
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
