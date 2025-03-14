package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.PocService;
import com.visp.gate_command.domain.dto.PocDto;
import com.visp.gate_command.domain.entity.Poc;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.PocMapper;
import com.visp.gate_command.repository.PocRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class PocServiceImpl implements PocService {
  private final PocRepository pocRepository;
  private final PocMapper pocMapper;

  @Override
  public PocDto create(PocDto pocDto) {
    pocDto.setCreatedAt(LocalDateTime.now());
    pocDto.setLastSync(LocalDateTime.now());
    return pocMapper.toDto(pocRepository.save(pocMapper.toEntity(pocDto)));
  }

  @Override
  public Optional<PocDto> update(PocDto pocDto) {
    final Optional<Poc> optionalPoc = pocRepository.findById(pocDto.getId());
    if (optionalPoc.isEmpty()) {
      throw new NotFoundException("Poc not found");
    }
    return Optional.of(pocMapper.toDto(pocRepository.save(pocMapper.toEntity(pocDto))));
  }

  @Override
  public void delete(Long id) {
    final Optional<Poc> optionalPoc = pocRepository.findById(id);
    if (optionalPoc.isEmpty()) {
      throw new NotFoundException("Poc not found");
    }
    pocRepository.deleteById(id);
  }

  @Override
  public List<PocDto> retrievePocByEntity(Long entityId) {
    return pocRepository.findByEntityId(entityId).stream().map(pocMapper::toDto).toList();
  }
}
