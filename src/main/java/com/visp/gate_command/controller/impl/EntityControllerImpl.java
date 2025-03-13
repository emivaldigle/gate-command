package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EntityService;
import com.visp.gate_command.controller.EntityController;
import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.exception.NotFoundException;
import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class EntityControllerImpl implements EntityController {

  private final EntityService residencyService;

  @Override
  public ResponseEntity<EntityDto> save(@RequestBody @Valid EntityDto entityDto) {
    return ResponseEntity.ok(residencyService.create(entityDto));
  }

  @Override
  public ResponseEntity<List<EntityDto>> retrieveAll() {
    return ResponseEntity.ok(residencyService.getAll());
  }

  @Override
  public ResponseEntity<Void> delete(@PathVariable Long id) {
    residencyService.delete(id);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<EntityDto> update(@RequestBody @Valid EntityDto entityDto) {
    Optional<EntityDto> entityDtoOptional = residencyService.update(entityDto);
    if (entityDtoOptional.isEmpty()) {
      throw new NotFoundException("User not found");
    }
    return ResponseEntity.ok(entityDtoOptional.get());
  }
}
