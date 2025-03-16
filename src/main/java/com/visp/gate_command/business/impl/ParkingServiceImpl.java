package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EntityService;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.ParkingMapper;
import com.visp.gate_command.repository.ParkingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable
public class ParkingServiceImpl implements ParkingService {
  private final ParkingMapper parkingMapper;
  private final ParkingRepository parkingRepository;
  private final EntityService entityService;

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
    return parkingRepository.findAllByEntityId(entityId).stream()
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

  @Override
  public void seed(Long entityId) {
    final var optionalEntity = entityService.findById(entityId);
    if (optionalEntity.isEmpty()) {
      throw new NotFoundException("no entity found for provided id");
    }
    IntStream.range(1, optionalEntity.get().getParkingSizeLimit())
        .forEach(i -> createParking(i, entityId, false));
    IntStream.range(1, optionalEntity.get().getVisitSizeLimit())
        .forEach(i -> createParking(i, entityId, true));
  }

  private void createParking(int index, Long entityId, boolean visit) {
    String identifier = index < 10 ? String.format("0%d", index) : String.valueOf(index);

    if (visit) {
      identifier = "V" + identifier;
    }
    ParkingDto parkingDto =
        ParkingDto.builder()
            .identifier(identifier)
            .entityId(entityId)
            .isForVisit(visit)
            .available(true)
            .createdAt(LocalDateTime.now())
            .build();
    parkingRepository.save(parkingMapper.toEntity(parkingDto));
  }
}
