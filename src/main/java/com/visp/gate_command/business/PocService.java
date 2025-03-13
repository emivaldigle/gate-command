package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.PocDto;
import java.util.List;
import java.util.Optional;

public interface PocService {
  PocDto create(PocDto pocDto);

  Optional<PocDto> update(PocDto pocDto);

  void delete(Long id);

  List<PocDto> retrievePocByEntity(Long entityId);

  List<PocDto> retrievePocByEntityAndStatus(Long entityId, String status);
}
