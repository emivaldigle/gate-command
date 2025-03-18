package com.visp.gate_command.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.visp.gate_command.business.impl.PocServiceImpl;
import com.visp.gate_command.domain.dto.PocDto;
import com.visp.gate_command.domain.entity.Poc;
import com.visp.gate_command.mapper.PocMapper;
import com.visp.gate_command.repository.PocRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PocServiceImplTest {

  @Mock private PocRepository pocRepository;
  @Mock private PocMapper pocMapper;
  @InjectMocks private PocServiceImpl pocService;

  @Test
  void testCreate() {
    PocDto pocDto = new PocDto();
    Poc poc = new Poc();
    when(pocMapper.toEntity(any())).thenReturn(poc);
    when(pocRepository.save(any())).thenReturn(poc);
    when(pocMapper.toDto(any())).thenReturn(pocDto);

    PocDto result = pocService.create(pocDto);
    assertNotNull(result);
    assertEquals(pocDto, result);
    verify(pocRepository, times(1)).save(any(Poc.class));
  }

  @Test
  void testUpdate() {
    UUID id = UUID.randomUUID();
    PocDto pocDto = new PocDto();
    pocDto.setId(id);
    Poc poc = new Poc();
    poc.setId(id);
    when(pocRepository.findById(id)).thenReturn(Optional.of(poc));
    when(pocRepository.save(poc)).thenReturn(poc);
    when(pocMapper.toEntity(pocDto)).thenReturn(poc);
    when(pocMapper.toDto(poc)).thenReturn(pocDto);
    Optional<PocDto> result = pocService.update(pocDto);

    assertTrue(result.isPresent());
    assertEquals(pocDto, result.get());
    verify(pocRepository, times(1)).save(poc);
  }

  @Test
  void testDelete() {
    UUID id = UUID.randomUUID();
    when(pocRepository.findById(id)).thenReturn(Optional.of(new Poc()));
    doNothing().when(pocRepository).deleteById(id);
    pocService.delete(id);
    verify(pocRepository, times(1)).deleteById(id);
  }
}
