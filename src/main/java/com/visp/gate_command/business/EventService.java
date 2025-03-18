package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.EventDto;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EventService {
  EventDto create(EventDto eventDto);

  List<EventDto> getAllByEntityAndDate(UUID entityId, LocalDateTime from, LocalDateTime to);

  Optional<EventDto> findByPocEntityIdAndPlate(UUID entityId, String plate);
}
