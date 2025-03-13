package com.visp.gate_command.domain.dto;

import com.visp.gate_command.domain.enums.PocType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PocDto {

  private Long id;

  private String identifier;

  private String name;

  private PocType type;

  private EntityDto entity;

  private String location;

  private LocalDateTime lastSync;

  private LocalDateTime createdAt;
}
