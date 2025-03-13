package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.VisitDto;
import java.util.List;
import java.util.Optional;

public interface VisitService {
  VisitDto create(VisitDto visitDto);

  Optional<VisitDto> update(VisitDto visitDto);

  void delete(Long id);

  List<VisitDto> getAllByEntityId(Long entityId);
}
