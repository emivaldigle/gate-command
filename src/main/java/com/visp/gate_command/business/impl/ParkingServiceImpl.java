package com.visp.gate_command.business.impl;

import static com.visp.gate_command.util.DateUtils.parseDate;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EntityService;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.entity.Parking;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.exception.ParkingUpdateException;
import com.visp.gate_command.mapper.ParkingMapper;
import com.visp.gate_command.messaging.MqttPublishService;
import com.visp.gate_command.repository.ParkingRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import static com.visp.gate_command.util.DateUtils.parseDate;
import static java.util.function.Predicate.not;

@Service
@RequiredArgsConstructor
@Loggable(level = "DEBUG")
public class ParkingServiceImpl implements ParkingService {
  private final ParkingMapper parkingMapper;
  private final ParkingRepository parkingRepository;
  private final EntityService entityService;
  private final MqttPublishService mqttPublishService;
  private static final String TOPIC_PARKING_CREATE = "parking/%s/create";
  private static final String TOPIC_PARKING_UPDATE = "parking/%s/update";
  private static final String TOPIC_PARKING_DELETE = "parking/%s/delete";

  @Override
  public ParkingDto create(ParkingDto parkingDto) {
    parkingDto.setId(UUID.randomUUID());
    parkingDto.setCreatedAt(LocalDateTime.now());
    parkingDto.setLastUpdatedAt(LocalDateTime.now());
    final var createdParking =
        parkingMapper.toDto(parkingRepository.save(parkingMapper.toEntity(parkingDto)));
    mqttPublishService.publishMessage(
        String.format(TOPIC_PARKING_CREATE, createdParking.getEntityId()), createdParking);
    return createdParking;
  }

  @Override
  public Optional<ParkingDto> update(ParkingDto parkingDto) {
    final var optionalParking = parkingRepository.findById(parkingDto.getId());
    if (optionalParking.isEmpty()) {
      throw new NotFoundException(
          String.format("parking with id %s not found", parkingDto.getId()));
    }
    if (!parkingDto.getIdentifier().equals(optionalParking.get().getIdentifier())) {
      throw new ParkingUpdateException("unable to update provided parking, the identifier does not match with one stored in data base");
    }
    if (Objects.isNull(parkingDto.getLastUpdatedAt())) {
      parkingDto.setLastUpdatedAt(LocalDateTime.now());
    }
    final var parkingEntity = parkingMapper.toEntity(parkingDto);
    final var updatedParking =
        parkingMapper.toDto(parkingRepository.save(parkingEntity));
    mqttPublishService.publishMessage(
        String.format(TOPIC_PARKING_UPDATE, updatedParking.getEntityId()), updatedParking);

    return Optional.of(updatedParking);
  }

  @Override
  public void delete(UUID parkingId) {
    final Optional<Parking> optionalParking = parkingRepository.findById(parkingId);
    if (optionalParking.isEmpty()) {
      throw new NotFoundException("parking not found");
    }
    parkingRepository.deleteById(parkingId);

    mqttPublishService.publishMessage(
        String.format(TOPIC_PARKING_DELETE, optionalParking.get().getEntity().getId()), parkingId);
  }

  @Override
  public List<ParkingDto> getAllByEntity(UUID entityId) {
    return parkingRepository.findAllByEntityId(entityId).stream()
        .map(parkingMapper::toDto)
        .toList();
  }

  @Override
  public List<ParkingDto> getAllByUser(UUID userId) {
    return parkingRepository.findAllByUserId(userId).stream().map(parkingMapper::toDto).toList();
  }

  @Override
  public Optional<ParkingDto> findByCurrentLicensePlate(String currentLicensePlate) {
    final var optionalParking = parkingRepository.findByCurrentLicensePlate(currentLicensePlate);
    return optionalParking.map(parkingMapper::toDto);
  }

  @Override
  public List<ParkingDto> findAllWithCreatedAtAndUpdatedAtGreaterThan(UUID entityId, String date) {
    LocalDateTime parsedDate = parseDate(date);
    return parkingRepository
        .findAllByEntityIdAndCreatedAtGreaterThanOrLastUpdatedAtGreaterThan(
            entityId, parsedDate, parsedDate)
        .stream()
        .map(parkingMapper::toDto)
        .toList();
  }

  @Override
  public Optional<ParkingDto> findByEntityIdAndIdentifier(UUID entityId, String identifier) {
    return parkingRepository.findByEntityIdAndIdentifier(entityId, identifier).map(parkingMapper::toDto);
  }

  @Override
  public void seed(UUID entityId) {
    final var optionalEntity = entityService.findById(entityId);
    if (optionalEntity.isEmpty()) {
      throw new NotFoundException("no entity found for provided id");
    }
    IntStream.range(1, optionalEntity.get().getParkingSizeLimit())
        .forEach(i -> createParking(i, entityId, false));
    IntStream.range(1, optionalEntity.get().getVisitSizeLimit())
        .forEach(i -> createParking(i, entityId, true));
  }

  private void createParking(int index, UUID entityId, boolean visit) {
    String identifier = index < 10 ? String.format("0%d", index) : String.valueOf(index);

    if (visit) {
      identifier = "V" + identifier;
    }
    ParkingDto parkingDto =
        ParkingDto.builder()
            .id(UUID.randomUUID())
            .identifier(identifier)
            .entityId(entityId)
            .isForVisit(visit)
            .lastUpdatedAt(LocalDateTime.now())
            .available(true)
            .createdAt(LocalDateTime.now())
            .build();
    parkingRepository.save(parkingMapper.toEntity(parkingDto));
  }
}
