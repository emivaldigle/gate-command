package com.visp.gate_command.business;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.visp.gate_command.business.impl.ParkingServiceImpl;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.entity.Parking;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.ParkingMapper;
import com.visp.gate_command.repository.ParkingRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ParkingServiceImplTest {

  @Mock private ParkingRepository parkingRepository;

  @Mock private ParkingMapper parkingMapper;

  @InjectMocks private ParkingServiceImpl parkingService;

  @Test
  void testCreateVisit() {
    ParkingDto visitDto = new ParkingDto();
    Parking visit = new Parking();
    when(parkingMapper.toEntity(any())).thenReturn(visit);
    when(parkingRepository.save(any())).thenReturn(visit);
    when(parkingMapper.toDto(any())).thenReturn(visitDto);

    ParkingDto output = parkingService.create(visitDto);
    assertNotNull(output);
    verify(parkingRepository, times(1)).save(any(Parking.class));
  }

  @Test
  void testUpdateVisit() {
    Parking visit = new Parking();
    when(parkingRepository.findById(any())).thenReturn(Optional.of(visit));
    ParkingDto visitDto = new ParkingDto();
    when(parkingMapper.toDto(any())).thenReturn(visitDto);
    when(parkingMapper.toEntity(any())).thenReturn(visit);
    when(parkingRepository.save(any())).thenReturn(visit);
    Optional<ParkingDto> updated = parkingService.update(visitDto);
    assertTrue(updated.isPresent());
    verify(parkingRepository, times(1)).save(any(Parking.class));
  }

  @Test
  void testUpdateParkingNotFound() {
    when(parkingRepository.findById(any())).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> parkingService.update(new ParkingDto()));
  }

  @Test
  void testFindByUser() {
    Long userId = 1L;
    when(parkingRepository.findAllByUserId(userId)).thenReturn(List.of(new Parking()));
    when(parkingMapper.toDto(any())).thenReturn(new ParkingDto());
    List<ParkingDto> parkingByUser = parkingService.getAllByUser(userId);
    assertNotNull(parkingByUser);
    assertEquals(1, parkingByUser.size());
  }

  @Test
  void testFindByEntityUser() {
    Long entityId = 1L;
    when(parkingRepository.findAllByEntityId(entityId)).thenReturn(List.of(new Parking()));
    when(parkingMapper.toDto(any())).thenReturn(new ParkingDto());
    List<ParkingDto> parkingByEntity = parkingService.getAllByEntity(entityId);
    assertNotNull(parkingByEntity);
    assertEquals(1, parkingByEntity.size());
  }
}
