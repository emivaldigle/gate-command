package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EventService;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.mapper.EventMapper;
import com.visp.gate_command.messaging.MqttPublishService;
import com.visp.gate_command.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;
  private final MqttPublishService mqttPublishService;

  @Override
  public EventDto create(EventDto eventDto) {
    eventDto.setId(UUID.randomUUID());
    return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(eventDto)));
  }

  @Override
  public List<EventDto> getAllByEntityAndDate(UUID entityId, LocalDateTime from, LocalDateTime to) {
    return eventRepository.findByPocEntityIdAndCreatedAtBetween(entityId, from, to).stream()
        .map(eventMapper::toDto)
        .toList();
  }

  @Override
  public Optional<EventDto> findByPocEntityIdAndPlate(UUID entityId, String plate) {
    return eventRepository.findFirstByPocEntityIdAndPlate(entityId, plate).map(eventMapper::toDto);
  }
}
