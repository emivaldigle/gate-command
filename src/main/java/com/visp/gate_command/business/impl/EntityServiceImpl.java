package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EntityService;
import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.entity.Entity;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.EntityMapper;
import com.visp.gate_command.repository.EntityRepository;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class EntityServiceImpl implements EntityService {

  private final EntityRepository repository;
  private final EntityMapper mapper;

  @Override
  public EntityDto create(EntityDto entityDto) {
    Entity entity = mapper.toEntity(entityDto);
    return mapper.toDto(repository.save(entity));
  }

  @Override
  public Optional<EntityDto> update(EntityDto entityDto) {
    final Optional<Entity> optionalEntity = repository.findById(entityDto.getId());
    if (optionalEntity.isEmpty()) {
      return Optional.empty();
    }
    return Optional.of(mapper.toDto(repository.save(mapper.toEntity(entityDto))));
  }

  @Override
  public void delete(Long id) {
    final Optional<Entity> optionalEntity = repository.findById(id);
    if (optionalEntity.isEmpty()) {
      throw new NotFoundException("entity not found");
    }
    repository.delete(optionalEntity.get());
  }

  @Override
  public List<EntityDto> getAll() {
    return repository.findAll().stream().map(mapper::toDto).toList();
  }

  @Override
  public Optional<EntityDto> findById(Long id) {
    final Optional<Entity> optionalEntity = repository.findById(id);
    return optionalEntity.map(mapper::toDto);
  }
}
