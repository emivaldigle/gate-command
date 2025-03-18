package com.visp.gate_command.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.visp.gate_command.business.PocService;
import com.visp.gate_command.controller.impl.PocControllerImpl;
import com.visp.gate_command.domain.dto.PocDto;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

@ExtendWith(MockitoExtension.class)
class PocControllerImplTest {

  @Mock private PocService pocService;

  @InjectMocks private PocControllerImpl pocController;

  @Test
  void testCreate() {
    PocDto pocDto = new PocDto();
    when(pocService.create(pocDto)).thenReturn(pocDto);

    ResponseEntity<PocDto> response = pocController.create(pocDto);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(pocDto, response.getBody());
    verify(pocService, times(1)).create(pocDto);
  }

  @Test
  void testUpdate() {
    PocDto pocDto = new PocDto();
    when(pocService.update(pocDto)).thenReturn(Optional.of(pocDto));

    ResponseEntity<PocDto> response = pocController.update(pocDto, UUID.randomUUID());

    assertEquals(200, response.getStatusCode().value());
    assertEquals(pocDto, response.getBody());
    verify(pocService, times(1)).update(pocDto);
  }

  @Test
  void testDelete() {
    UUID id = UUID.randomUUID();
    doNothing().when(pocService).delete(id);

    ResponseEntity<Void> response = pocController.delete(id);

    assertEquals(204, response.getStatusCode().value());
    verify(pocService, times(1)).delete(id);
  }

  @Test
  void testRetrievePocByEntity() {
    UUID entityId = UUID.randomUUID();
    when(pocService.retrievePocByEntity(entityId)).thenReturn(Collections.emptyList());

    ResponseEntity<List<PocDto>> response = pocController.retrievePocByEntity(entityId);

    assertEquals(200, response.getStatusCode().value());
    assertEquals(Collections.emptyList(), response.getBody());
    verify(pocService, times(1)).retrievePocByEntity(entityId);
  }
}
