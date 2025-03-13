package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.EntityDto;
import java.util.List;
import java.util.Optional;

public interface EntityService {
  EntityDto create(EntityDto entityDto);

  Optional<EntityDto> update(EntityDto entityDto);

  void delete(Long id);

  List<EntityDto> getAll();

  Optional<EntityDto> findById(Long id);
}
