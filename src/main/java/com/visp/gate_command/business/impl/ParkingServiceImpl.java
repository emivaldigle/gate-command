package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.ParkingMapper;
import com.visp.gate_command.repository.ParkingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class ParkingServiceImpl implements ParkingService {
  private final ParkingMapper parkingMapper;
  private final ParkingRepository parkingRepository;

  @Override
  public ParkingDto create(ParkingDto parkingDto) {
    parkingDto.setCreatedAt(LocalDateTime.now());
    return parkingMapper.toDto(parkingRepository.save(parkingMapper.toEntity(parkingDto)));
  }

  @Override
  public Optional<ParkingDto> update(ParkingDto visitDto) {
    final var optionalParking = parkingRepository.findById(visitDto.getId());
    if (optionalParking.isEmpty()) {
      throw new NotFoundException(String.format("parking with id %s not found", visitDto.getId()));
    }
    return Optional.of(
        parkingMapper.toDto(parkingRepository.save(parkingMapper.toEntity(visitDto))));
  }

  @Override
  public List<ParkingDto> getAllByEntity(Long entityId) {
    return parkingRepository.findAllByUserEntityId(entityId).stream()
        .map(parkingMapper::toDto)
        .toList();
  }

  @Override
  public List<ParkingDto> getAllByUser(Long userId) {
    return parkingRepository.findAllByUserId(userId).stream().map(parkingMapper::toDto).toList();
  }

  @Override
  public Optional<ParkingDto> findByCurrentLicensePlate(String currentLicensePlate) {
    final var optionalParking = parkingRepository.findByCurrentLicensePlate(currentLicensePlate);
      return optionalParking.map(parkingMapper::toDto);
  }
}
