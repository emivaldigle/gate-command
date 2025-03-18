package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EntityService;
import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.entity.Entity;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.EntityMapper;
import com.visp.gate_command.messaging.MqttPublishService;
import com.visp.gate_command.repository.EntityRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable(level = "DEBUG")
public class EntityServiceImpl implements EntityService {

  private final EntityRepository repository;
  private final EntityMapper mapper;
  private final MqttPublishService mqttPublishService;

  @Override
  public EntityDto create(EntityDto entityDto) {
    entityDto.setCreatedAt(LocalDateTime.now());
    entityDto.setLastUpdatedAt(LocalDateTime.now());
    entityDto.setId(UUID.randomUUID());
    Entity entity = mapper.toEntity(entityDto);
    return mapper.toDto(repository.save(entity));
  }

  @Override
  public Optional<EntityDto> update(EntityDto entityDto) {
    final Optional<Entity> optionalEntity = repository.findById(entityDto.getId());
    if (optionalEntity.isEmpty()) {
      return Optional.empty();
    }
    entityDto.setLastUpdatedAt(LocalDateTime.now());
    final var entity = mapper.toDto(repository.save(mapper.toEntity(entityDto)));
    mqttPublishService.publishMessage(String.format("entity/%s/update", entity.getId()), entity);
    return Optional.of(entity);
  }

  @Override
  public void delete(UUID id) {
    final Optional<Entity> optionalEntity = repository.findById(id);
    if (optionalEntity.isEmpty()) {
      throw new NotFoundException("entity not found");
    }
    repository.deleteById(id);
  }

  @Override
  public List<EntityDto> getAll() {
    return repository.findAll().stream().map(mapper::toDto).toList();
  }

  @Override
  public Optional<EntityDto> findById(UUID id) {
    final Optional<Entity> optionalEntity = repository.findById(id);
    return optionalEntity.map(mapper::toDto);
  }
}
