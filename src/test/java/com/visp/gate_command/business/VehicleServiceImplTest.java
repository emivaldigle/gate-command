package com.visp.gate_command.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.visp.gate_command.business.impl.VehicleServiceImpl;
import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import com.visp.gate_command.domain.entity.Vehicle;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.VehicleMapper;
import com.visp.gate_command.repository.VehicleRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VehicleServiceImplTest {

  @Mock private VehicleRepository vehicleRepository;

  @Mock private VehicleMapper vehicleMapper;

  @InjectMocks private VehicleServiceImpl vehicleService;

  @Test
  void testCreateVehicle() {
    // Arrange
    VehicleDto inputDto = new VehicleDto();
    inputDto.setPlate("ABC123");

    Vehicle entity = new Vehicle();
    entity.setId(UUID.randomUUID());
    entity.setPlate("ABC123");
    entity.setCreatedAt(LocalDateTime.now());

    when(vehicleMapper.toEntity(inputDto)).thenReturn(entity);
    when(vehicleRepository.save(entity)).thenReturn(entity);
    when(vehicleMapper.toDto(entity)).thenReturn(new VehicleDto());

    // Act
    VehicleDto result = vehicleService.create(inputDto);

    // Assert
    assertNotNull(result);
    verify(vehicleMapper, times(1)).toEntity(inputDto);
    verify(vehicleRepository, times(1)).save(entity);
    verify(vehicleMapper, times(1)).toDto(entity);
  }

  @Test
  void testUpdateVehicle_Success() {
    // Arrange
    UUID vehicleId = UUID.randomUUID();
    VehicleDto inputDto = new VehicleDto();
    inputDto.setId(vehicleId);
    inputDto.setPlate("XYZ789");

    Vehicle existingVehicle = new Vehicle();
    existingVehicle.setId(vehicleId);
    existingVehicle.setPlate("ABC123");

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(existingVehicle));
    when(vehicleMapper.toEntity(inputDto)).thenReturn(existingVehicle);
    when(vehicleRepository.save(existingVehicle)).thenReturn(existingVehicle);
    when(vehicleMapper.toDto(existingVehicle)).thenReturn(inputDto);

    // Act
    Optional<VehicleDto> result = vehicleService.update(inputDto);

    // Assert
    assertTrue(result.isPresent());
    assertEquals("XYZ789", result.get().getPlate());
    verify(vehicleRepository, times(1)).findById(vehicleId);
    verify(vehicleMapper, times(1)).toEntity(inputDto);
    verify(vehicleRepository, times(1)).save(existingVehicle);
    verify(vehicleMapper, times(1)).toDto(existingVehicle);
  }

  @Test
  void testUpdateVehicle_NotFound() {
    // Arrange
    UUID vehicleId = UUID.randomUUID();
    VehicleDto inputDto = new VehicleDto();
    inputDto.setId(vehicleId);

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception =
        assertThrows(NotFoundException.class, () -> vehicleService.update(inputDto));
    assertEquals("not found vehicle 1", exception.getMessage());
    verify(vehicleRepository, times(1)).findById(vehicleId);
  }

  @Test
  void testDeleteVehicle_Success() {
    // Arrange
    UUID vehicleId = UUID.randomUUID();
    Vehicle vehicle = new Vehicle();
    vehicle.setId(vehicleId);

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.of(vehicle));

    // Act
    vehicleService.delete(vehicleId, UUID.randomUUID());

    // Assert
    verify(vehicleRepository, times(1)).findById(vehicleId);
    verify(vehicleRepository, times(1)).deleteById(vehicleId);
  }

  @Test
  void testDeleteVehicle_NotFound() {
    // Arrange
    UUID vehicleId = UUID.randomUUID();

    when(vehicleRepository.findById(vehicleId)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception =
        assertThrows(NotFoundException.class, () -> vehicleService.delete(vehicleId, UUID.randomUUID()));
    assertEquals("not found vehicle 1", exception.getMessage());
    verify(vehicleRepository, times(1)).findById(vehicleId);
  }

  @Test
  void testGetByUserId() {
    // Arrange
    UUID userId = UUID.randomUUID();
    UUID id = UUID.randomUUID();
    Vehicle vehicle = new Vehicle();
    vehicle.setId(id);
    vehicle.setPlate("ABC123");

    VehicleDto vehicleDto = new VehicleDto();
    vehicleDto.setId(id);
    vehicleDto.setPlate("ABC123");

    when(vehicleRepository.findByUserId(userId)).thenReturn(List.of(vehicle));
    when(vehicleMapper.toDto(vehicle)).thenReturn(vehicleDto);

    // Act
    List<VehicleDto> result = vehicleService.getByUserId(userId);

    // Assert
    assertEquals(1, result.size());
    assertEquals("ABC123", result.getFirst().getPlate());
    verify(vehicleRepository, times(1)).findByUserId(userId);
    verify(vehicleMapper, times(1)).toDto(vehicle);
  }

  @Test
  void testGetAllByEntity() {
    // Arrange
    UUID id = UUID.randomUUID();
    UUID entityId = UUID.randomUUID();
    Vehicle vehicle = new Vehicle();
    vehicle.setId(id);
    vehicle.setPlate("ABC123");

    VehicleDto vehicleDto = new VehicleDto();
    vehicleDto.setId(id);
    vehicleDto.setPlate("ABC123");

    when(vehicleRepository.findByUserEntityId(entityId)).thenReturn(List.of(vehicle));
    when(vehicleMapper.toDto(vehicle)).thenReturn(vehicleDto);

    // Act
    List<VehicleDto> result = vehicleService.getAllByEntity(entityId);

    // Assert
    assertEquals(1, result.size());
    assertEquals("ABC123", result.getFirst().getPlate());
    verify(vehicleRepository, times(1)).findByUserEntityId(entityId);
    verify(vehicleMapper, times(1)).toDto(vehicle);
  }

  @Test
  void testGetAllSummariesByEntity() {
    // Arrange
    UUID id = UUID.randomUUID();
    UUID entityId = UUID.randomUUID();
    Vehicle vehicle = new Vehicle();
    vehicle.setId(id);
    vehicle.setPlate("ABC123");

    VehicleSummaryDto summaryDto = new VehicleSummaryDto(id, "ABC123", false);

    when(vehicleRepository.findByUserEntityId(entityId)).thenReturn(List.of(vehicle));
    when(vehicleMapper.toVehicleSummaryDto(vehicle)).thenReturn(summaryDto);

    // Act
    List<VehicleSummaryDto> result = vehicleService.getAllSummariesByEntity(entityId);

    // Assert
    assertEquals(1, result.size());
    assertEquals("ABC123", result.getFirst().getPlate());
    verify(vehicleRepository, times(1)).findByUserEntityId(entityId);
    verify(vehicleMapper, times(1)).toVehicleSummaryDto(vehicle);
  }
}
