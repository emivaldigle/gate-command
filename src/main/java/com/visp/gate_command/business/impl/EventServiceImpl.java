package com.visp.gate_command.business.impl;

import com.visp.gate_command.business.EventService;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.business.VehicleService;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.enums.EventType;
import com.visp.gate_command.mapper.EventMapper;
import com.visp.gate_command.repository.EventRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;
  private final ParkingService parkingService;
  private final VehicleService vehicleService;

  @Override
  public EventDto create(EventDto eventDto) {
    return null;
  }

  @Override
  public List<EventDto> getAllByEntity(Long entityId) {
    return List.of();
  }
}
