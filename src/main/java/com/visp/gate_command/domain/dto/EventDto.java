package com.visp.gate_command.domain.dto;

import com.visp.gate_command.domain.enums.EventType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventDto {

  private Long id;

  private PocDto poc;

  private String plate;

  private EventType type;

  private LocalDateTime createdAt;
}
