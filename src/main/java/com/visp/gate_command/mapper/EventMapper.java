package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.domain.entity.Event;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EventMapper {

  @Mapping(source = "pocId", target = "poc.id")
  Event toEntity(EventDto eventDto);

  @Mapping(source = "poc.id", target = "pocId")
  EventDto toDto(Event event);
}
