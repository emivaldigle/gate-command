package com.visp.gate_command.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.visp.gate_command.business.impl.EntityServiceImpl;
import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.entity.Entity;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.EntityMapper;
import com.visp.gate_command.repository.EntityRepository;
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
class EntityServiceImplTest {

  @Mock private EntityRepository repository;

  @Mock private EntityMapper mapper;

  @InjectMocks private EntityServiceImpl entityService;

  @Test
  void testCreate() {
    // Arrange
    EntityDto entityDto = new EntityDto();
    entityDto.setName("Test Entity");

    Entity entity = new Entity();
    entity.setId(UUID.randomUUID());
    entity.setName("Test Entity");
    entity.setCreatedAt(LocalDateTime.now());
    entity.setLastUpdatedAt(LocalDateTime.now());

    when(mapper.toEntity(entityDto)).thenReturn(entity);
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toDto(entity)).thenReturn(entityDto);

    // Act
    EntityDto result = entityService.create(entityDto);

    // Assert
    assertNotNull(result);
    assertEquals("Test Entity", result.getName());
    verify(repository, times(1)).save(any(Entity.class));
  }

  @Test
  void testUpdate() {
    // Arrange
    UUID id = UUID.randomUUID();
    EntityDto entityDto = new EntityDto();
    entityDto.setId(id);
    entityDto.setName("Updated Entity");

    Entity entity = new Entity();
    entity.setId(id);
    entity.setName("Updated Entity");

    when(repository.findById(UUID.randomUUID())).thenReturn(Optional.of(entity));
    when(repository.save(entity)).thenReturn(entity);
    when(mapper.toEntity(entityDto)).thenReturn(entity);
    when(mapper.toDto(entity)).thenReturn(entityDto);

    // Act
    Optional<EntityDto> result = entityService.update(entityDto);

    // Assert
    assertTrue(result.isPresent());
    assertEquals("Updated Entity", result.get().getName());
    verify(repository, times(1)).findById(UUID.randomUUID());
    verify(repository, times(1)).save(entity);
  }

  @Test
  void testDelete() {
    // Arrange
    UUID entityId = UUID.randomUUID();
    Entity entity = new Entity();
    entity.setId(entityId);

    when(repository.findById(entityId)).thenReturn(Optional.of(entity));

    // Act
    entityService.delete(entityId);

    // Assert
    verify(repository, times(1)).deleteById(entityId);
  }

  @Test
  void testDeleteThrowsNotFoundException() {
    // Arrange
    UUID entityId = UUID.randomUUID();
    when(repository.findById(entityId)).thenReturn(Optional.empty());

    // Act & Assert
    NotFoundException exception =
        assertThrows(NotFoundException.class, () -> entityService.delete(entityId));
    assertEquals("entity not found", exception.getMessage());
    verify(repository, never()).delete(any());
  }

  @Test
  void testGetAll() {
    // Arrange
    Entity entity1 = new Entity();
    entity1.setId(UUID.randomUUID());
    entity1.setName("Entity 1");

    Entity entity2 = new Entity();
    entity2.setId(UUID.randomUUID());
    entity2.setName("Entity 2");

    List<Entity> entities = List.of(entity1, entity2);

    EntityDto dto1 = new EntityDto();
    dto1.setId(UUID.randomUUID());
    dto1.setName("Entity 1");

    EntityDto dto2 = new EntityDto();
    dto2.setId(UUID.randomUUID());
    dto2.setName("Entity 2");

    when(repository.findAll()).thenReturn(entities);
    when(mapper.toDto(entity1)).thenReturn(dto1);
    when(mapper.toDto(entity2)).thenReturn(dto2);

    // Act
    List<EntityDto> result = entityService.getAll();

    // Assert
    assertNotNull(result);
    assertEquals(2, result.size());
    assertEquals("Entity 1", result.get(0).getName());
    assertEquals("Entity 2", result.get(1).getName());
  }

  @Test
  void testFindById() {
    // Arrange
    UUID entityId = UUID.randomUUID();
    Entity entity = new Entity();
    entity.setId(entityId);
    entity.setName("Test Entity");

    EntityDto dto = new EntityDto();
    dto.setId(entityId);
    dto.setName("Test Entity");

    when(repository.findById(entityId)).thenReturn(Optional.of(entity));
    when(mapper.toDto(entity)).thenReturn(dto);

    // Act
    Optional<EntityDto> result = entityService.findById(entityId);

    // Assert
    assertTrue(result.isPresent());
    assertEquals("Test Entity", result.get().getName());
    verify(repository, times(1)).findById(entityId);
  }
}
