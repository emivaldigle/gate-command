package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.entity.Entity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityMapper {

  Entity toEntity(EntityDto entityDto);

  EntityDto toDto(Entity entity);
}
