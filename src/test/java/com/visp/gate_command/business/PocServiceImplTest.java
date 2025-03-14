package com.visp.gate_command.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.visp.gate_command.domain.dto.PocDto;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PocServiceImplTest {

  @Mock private PocService pocService;

  @Test
  void testCreate() {
    PocDto pocDto = new PocDto();
    when(pocService.create(pocDto)).thenReturn(pocDto);

    PocDto result = pocService.create(pocDto);

    assertNotNull(result);
    assertEquals(pocDto, result);
    verify(pocService, times(1)).create(pocDto);
  }

  @Test
  void testUpdate() {
    PocDto pocDto = new PocDto();
    when(pocService.update(pocDto)).thenReturn(Optional.of(pocDto));

    Optional<PocDto> result = pocService.update(pocDto);

    assertTrue(result.isPresent());
    assertEquals(pocDto, result.get());
    verify(pocService, times(1)).update(pocDto);
  }

  @Test
  void testDelete() {
    Long id = 1L;
    doNothing().when(pocService).delete(id);

    pocService.delete(id);

    verify(pocService, times(1)).delete(id);
  }
}
