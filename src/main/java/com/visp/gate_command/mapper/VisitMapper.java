package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.VisitDto;
import com.visp.gate_command.domain.entity.Visit;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface VisitMapper {
  Visit toEntity(VisitDto visitDto);

  VisitDto toDto(Visit visit);
}
