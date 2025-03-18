package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.EntityDto;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EntityService {
  EntityDto create(EntityDto entityDto);

  Optional<EntityDto> update(EntityDto entityDto);

  void delete(UUID id);

  List<EntityDto> getAll();

  Optional<EntityDto> findById(UUID id);
}
