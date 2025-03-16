package com.visp.gate_command.business.impl;

import com.visp.gate_command.business.EventService;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.mapper.EventMapper;
import com.visp.gate_command.repository.EventRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

  private final EventRepository eventRepository;
  private final EventMapper eventMapper;

  @Override
  public EventDto create(EventDto eventDto) {
    return eventMapper.toDto(eventRepository.save(eventMapper.toEntity(eventDto)));
  }

  @Override
  public List<EventDto> getAllByEntityAndDate(Long entityId, LocalDateTime from, LocalDateTime to) {
    return eventRepository.findByPocEntityIdAndCreatedAtBetween(entityId, from, to).stream()
        .map(eventMapper::toDto)
        .toList();
  }
}
