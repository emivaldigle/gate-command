package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.EventDto;
import java.util.List;

public interface EventService {
  EventDto create(EventDto eventDto);

  List<EventDto> getAllByEntity(Long entityId);
}
