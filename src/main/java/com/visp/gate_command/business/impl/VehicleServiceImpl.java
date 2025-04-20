package com.visp.gate_command.business.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.UserService;
import com.visp.gate_command.business.VehicleService;
import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import com.visp.gate_command.domain.entity.Vehicle;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.VehicleMapper;
import com.visp.gate_command.messaging.MqttPublishService;
import com.visp.gate_command.repository.VehicleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Loggable(level = "DEBUG")
public class VehicleServiceImpl implements VehicleService {
  private final VehicleMapper vehicleMapper;
  private final VehicleRepository vehicleRepository;
  private final UserService userService;
  private final MqttPublishService mqttPublishService;
  private static final String TOPIC_VEHICLE_CREATE = "vehicle/%s/create";
  private static final String TOPIC_VEHICLE_UPDATE = "vehicle/%s/update";
  private static final String TOPIC_VEHICLE_DELETE = "vehicle/%s/delete";

  @Override
  public VehicleDto create(VehicleDto vehicleDto) {
    final var optionalUser = userService.findByUserId(vehicleDto.getUser().getId());
    if (optionalUser.isEmpty()) {
      throw new NotFoundException("Unable to create vehicle, user provided not found");
    }
    vehicleDto.setCreatedAt(LocalDateTime.now());
    vehicleDto.setLastUpdatedAt(LocalDateTime.now());
    vehicleDto.setId(UUID.randomUUID());

    final var createdVehicle =
        vehicleMapper.toDto(vehicleRepository.save(vehicleMapper.toEntity(vehicleDto)));
    mqttPublishService.publishMessage(
        String.format(TOPIC_VEHICLE_CREATE, optionalUser.get().getEntity().getId()),
        createdVehicle);
    return createdVehicle;
  }

  @Override
  public Optional<VehicleDto> update(VehicleDto vehicleDto) {
    final Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleDto.getId());
    if (optionalVehicle.isEmpty()) {
      throw new NotFoundException(String.format("not found vehicle %s", vehicleDto.getId()));
    }
    final var optionalUser = userService.findByUserId(vehicleDto.getUser().getId());
    if (optionalUser.isEmpty()) {
      throw new NotFoundException("Unable to create vehicle, user provided not found");
    }
    vehicleDto.setLastUpdatedAt(LocalDateTime.now());
    final var updatedVehicle =
        vehicleMapper.toDto(vehicleRepository.save(vehicleMapper.toEntity(vehicleDto)));
    mqttPublishService.publishMessage(
        String.format(TOPIC_VEHICLE_UPDATE, optionalUser.get().getEntity().getId()),
        updatedVehicle);
    return Optional.of(updatedVehicle);
  }

  @Override
  public void delete(UUID userId, UUID entityId) {
    final Optional<Vehicle> optionalVehicle = vehicleRepository.findById(userId);
    if (optionalVehicle.isEmpty()) {
      throw new NotFoundException(String.format("not found vehicle %s", userId));
    }
    vehicleRepository.deleteById(userId);
    mqttPublishService.publishMessage(String.format(TOPIC_VEHICLE_DELETE, entityId), userId);
  }

  @Override
  public List<VehicleDto> getByUserId(UUID userId) {
    return vehicleRepository.findByUserId(userId).stream().map(vehicleMapper::toDto).toList();
  }

  @Override
  public List<VehicleDto> getAllByEntity(UUID entityId) {
    return vehicleRepository.findByUserEntityId(entityId).stream()
        .map(vehicleMapper::toDto)
        .toList();
  }

  @Override
  public List<VehicleSummaryDto> getAllSummariesByEntity(UUID entityId) {
    final List<Vehicle> vehiclesByEntity = vehicleRepository.findByUserEntityId(entityId);
    return vehiclesByEntity.stream().map(vehicleMapper::toVehicleSummaryDto).toList();
  }

  @Override
  public Optional<VehicleDto> findByLicensePlateAndEntityId(String licensePlate, UUID entityId) {
    final Optional<Vehicle> optionalVehicle =
        vehicleRepository.findByPlateAndUserEntityId(licensePlate, entityId);
    if (optionalVehicle.isEmpty()) {
      throw new NotFoundException(
          String.format("vehicle with license plate %s no found", licensePlate));
    }
    return Optional.of(vehicleMapper.toDto(optionalVehicle.get()));
  }
}
