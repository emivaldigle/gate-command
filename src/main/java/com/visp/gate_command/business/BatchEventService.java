package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.EventBatchDto;

public interface BatchEventService {
  void saveBatch(EventBatchDto events);
}
