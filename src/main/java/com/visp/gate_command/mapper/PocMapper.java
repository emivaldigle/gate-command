package com.visp.gate_command.mapper;

import com.visp.gate_command.domain.dto.PocDto;
import com.visp.gate_command.domain.entity.Poc;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PocMapper {
  Poc toEntity(PocDto pocDto);

  PocDto toDto(Poc poc);
}
