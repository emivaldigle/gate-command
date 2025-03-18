package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.PocDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PocService {
  PocDto create(PocDto pocDto);

  Optional<PocDto> update(PocDto pocDto);

  void delete(UUID id);

  List<PocDto> retrievePocByEntity(UUID entityId);
}
