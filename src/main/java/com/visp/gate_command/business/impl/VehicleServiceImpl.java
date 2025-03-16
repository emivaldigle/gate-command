package com.visp.gate_command.business.impl;

import com.visp.gate_command.business.VehicleService;
import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import com.visp.gate_command.domain.entity.Vehicle;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.VehicleMapper;
import com.visp.gate_command.repository.VehicleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VehicleServiceImpl implements VehicleService {
  private final VehicleMapper vehicleMapper;
  private final VehicleRepository vehicleRepository;

  @Override
  public VehicleDto create(VehicleDto vehicleDto) {
    vehicleDto.setCreatedAt(LocalDateTime.now());
    return vehicleMapper.toDto(vehicleRepository.save(vehicleMapper.toEntity(vehicleDto)));
  }

  @Override
  public Optional<VehicleDto> update(VehicleDto vehicleDto) {
    final Optional<Vehicle> optionalVehicle = vehicleRepository.findById(vehicleDto.getId());
    if (optionalVehicle.isEmpty()) {
      throw new NotFoundException(String.format("not found vehicle %s", vehicleDto.getId()));
    }
    return Optional.of(
        vehicleMapper.toDto(vehicleRepository.save(vehicleMapper.toEntity(vehicleDto))));
  }

  @Override
  public void delete(Long id) {
    final Optional<Vehicle> optionalVehicle = vehicleRepository.findById(id);
    if (optionalVehicle.isEmpty()) {
      throw new NotFoundException(String.format("not found vehicle %s", id));
    }
    vehicleRepository.deleteById(id);
  }

  @Override
  public List<VehicleDto> getByUserId(Long userId) {
    return vehicleRepository.findByUserId(userId).stream().map(vehicleMapper::toDto).toList();
  }

  @Override
  public List<VehicleDto> getAllByEntity(Long entityId) {
    return vehicleRepository.findByUserEntityId(entityId).stream()
        .map(vehicleMapper::toDto)
        .toList();
  }

  @Override
  public List<VehicleSummaryDto> getAllSummariesByEntity(Long entityId) {
    final List<Vehicle> vehiclesByEntity = vehicleRepository.findByUserEntityId(entityId);
    return vehiclesByEntity.stream().map(vehicleMapper::toVehicleSummaryDto).toList();
  }

  @Override
  public Optional<VehicleDto> findByLicensePlate(String licensePlate) {
    final Optional<Vehicle> optionalVehicle = vehicleRepository.findByPlate(licensePlate);
    if (optionalVehicle.isEmpty()) {
      throw new NotFoundException(
          String.format("vehicle with license plate %s no found", licensePlate));
    }
    return Optional.of(vehicleMapper.toDto(optionalVehicle.get()));
  }
}
