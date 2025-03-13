package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.domain.entity.Event;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

  Event toEntity(EventDto eventDto);

  EventDto toDto(Event event);
}
