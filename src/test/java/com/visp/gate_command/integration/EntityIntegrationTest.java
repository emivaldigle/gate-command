package com.visp.gate_command.integration;

import static com.visp.gate_command.utils.TestObjectFactory.buildEntityDto;
import static org.junit.jupiter.api.Assertions.*;

import com.visp.gate_command.domain.dto.EntityDto;
import java.util.Objects;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("intTest")
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class EntityIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;

  private TestRestTemplate getAuthenticatedRestTemplate() {
    return restTemplate.withBasicAuth("root", "root");
  }

  @Test
  void testCreateAndGetEntity() {
    // Arrange
    EntityDto entityDto = buildEntityDto();

    // Act: Create Entity
    ResponseEntity<EntityDto> createResponse =
        getAuthenticatedRestTemplate().postForEntity("/entities", entityDto, EntityDto.class);

    // Assert: Verify creation
    assertEquals(HttpStatus.OK, createResponse.getStatusCode());
    assertNotNull(createResponse.getBody());
    assertEquals("name", createResponse.getBody().getName());
  }

  @Test
  void testUpdateEntity() {
    // Arrange: Create an entity
    EntityDto entityDto = buildEntityDto();
    ResponseEntity<EntityDto> createResponse =
        getAuthenticatedRestTemplate().postForEntity("/entities", entityDto, EntityDto.class);

    EntityDto updatedDto = createResponse.getBody();
    Objects.requireNonNull(updatedDto).setName("Updated Name");
    HttpEntity<EntityDto> updateRequest = new HttpEntity<>(Objects.requireNonNull(updatedDto));
    ResponseEntity<EntityDto> updateResponse =
        getAuthenticatedRestTemplate()
            .exchange(
                "/entities/" + createResponse.getBody().getId(),
                HttpMethod.PATCH,
                updateRequest,
                EntityDto.class);

    // Assert: Verify update
    assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
    assertEquals("Updated Name", Objects.requireNonNull(updateResponse.getBody()).getName());
  }

  @Test
  void testDeleteEntity() {
    // Arrange: Create an entity
    EntityDto entityDto = buildEntityDto();
    ResponseEntity<EntityDto> createResponse =
        getAuthenticatedRestTemplate().postForEntity("/entities", entityDto, EntityDto.class);

    assertEquals(HttpStatus.OK, createResponse.getStatusCode());

    UUID entityId = Objects.requireNonNull(createResponse.getBody()).getId();

    // Act: Delete the entity
    getAuthenticatedRestTemplate().delete("/entities/" + entityId);

    // Assert: Verify deletion
    ResponseEntity<EntityDto[]> getResponse =
        getAuthenticatedRestTemplate().getForEntity("/entities", EntityDto[].class);

    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(0, Objects.requireNonNull(getResponse.getBody()).length);
  }

  @Test
  void testGetAllEntities() {
    // Arrange: Create multiple entities
    EntityDto entity1 = buildEntityDto();
    entity1.setName("Entity 1");
    getAuthenticatedRestTemplate().postForEntity("/entities", entity1, EntityDto.class);

    EntityDto entity2 = buildEntityDto();
    entity2.setName("Entity 2");

    getAuthenticatedRestTemplate().postForEntity("/entities", entity2, EntityDto.class);

    // Act: Retrieve all entities
    ResponseEntity<EntityDto[]> getResponse =
        getAuthenticatedRestTemplate().getForEntity("/entities", EntityDto[].class);

    // Assert: Verify retrieval
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(2, Objects.requireNonNull(getResponse.getBody()).length);
    assertEquals("Entity 1", getResponse.getBody()[0].getName());
    assertEquals("Entity 2", getResponse.getBody()[1].getName());
  }
}
