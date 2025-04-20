package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.PocService;
import com.visp.gate_command.domain.dto.PocDto;
import com.visp.gate_command.domain.entity.Poc;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.PocMapper;
import com.visp.gate_command.messaging.MqttPublishService;
import com.visp.gate_command.repository.PocRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable(level = "DEBUG")
public class PocServiceImpl implements PocService {
  private final PocRepository pocRepository;
  private final PocMapper pocMapper;
  private final MqttPublishService mqttPublishService;
  private static final String TOPIC_POC_CREATE = "poc/%s/create";
  private static final String TOPIC_POC_UPDATE = "poc/%s/update";
  private static final String TOPIC_POC_DELETE = "poc/%s/delete";

  @Override
  public PocDto create(PocDto pocDto) {
    pocDto.setCreatedAt(LocalDateTime.now());
    pocDto.setLastUpdatedAt(LocalDateTime.now());
    pocDto.setLastSync(LocalDateTime.now());
    pocDto.setId(UUID.randomUUID());
    final var createdPoc = pocMapper.toDto(pocRepository.save(pocMapper.toEntity(pocDto)));
    mqttPublishService.publishMessage(
        String.format(TOPIC_POC_CREATE, createdPoc.getEntity().getId()), createdPoc);
    return createdPoc;
  }

  @Override
  public Optional<PocDto> update(PocDto pocDto) {
    final Optional<Poc> optionalPoc = pocRepository.findById(pocDto.getId());
    if (optionalPoc.isEmpty()) {
      throw new NotFoundException("Poc not found");
    }
    pocDto.setLastUpdatedAt(LocalDateTime.now());
    final var updatedPoc = pocMapper.toDto(pocRepository.save(pocMapper.toEntity(pocDto)));
    mqttPublishService.publishMessage(
        String.format(TOPIC_POC_UPDATE, updatedPoc.getEntity().getId()), updatedPoc);
    return Optional.of(updatedPoc);
  }

  @Override
  public void delete(UUID id) {
    final Optional<Poc> optionalPoc = pocRepository.findById(id);
    if (optionalPoc.isEmpty()) {
      throw new NotFoundException("Poc not found");
    }
    pocRepository.deleteById(id);
    mqttPublishService.publishMessage(
        String.format(TOPIC_POC_DELETE, optionalPoc.get().getEntity().getId()), id);
  }

  @Override
  public List<PocDto> retrievePocByEntity(UUID entityId) {
    return pocRepository.findByEntityId(entityId).stream().map(pocMapper::toDto).toList();
  }
}
