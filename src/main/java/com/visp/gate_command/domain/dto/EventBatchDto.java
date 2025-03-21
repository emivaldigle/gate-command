package com.visp.gate_command.domain.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EventBatchDto {
  private String batchId;
  private List<EventDto> events;
}
