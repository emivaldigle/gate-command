package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EventService;
import com.visp.gate_command.controller.EventController;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.exception.NotFoundException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class EventControllerImpl implements EventController {
  private final EventService eventService;

  @Override
  public ResponseEntity<EventDto> findLastEventByEntityAndPlate(
      UUID entityId, String licensePlate) {
    return eventService
        .findByPocEntityIdAndPlate(entityId, licensePlate)
        .map(ResponseEntity::ok)
        .orElseThrow(() -> new NotFoundException("Event not found"));
  }
}
