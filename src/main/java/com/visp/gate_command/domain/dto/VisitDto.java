package com.visp.gate_command.domain.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto {
  private Long id;

  private UserDto resident;

  private String name;

  private String lastName;

  private String vehiclePlate;

  private LocalDateTime expirationDate;

  private LocalDateTime createdAt;
}
