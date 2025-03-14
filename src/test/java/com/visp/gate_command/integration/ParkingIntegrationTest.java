package com.visp.gate_command.integration;

import static com.visp.gate_command.utils.TestObjectFactory.buildEntityDto;
import static com.visp.gate_command.utils.TestObjectFactory.buildParkingDto;
import static com.visp.gate_command.utils.TestObjectFactory.buildUserDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.enums.UserType;
import java.util.Objects;
import org.junit.jupiter.api.BeforeEach;
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
class ParkingIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;
  private ResponseEntity<EntityDto> createEntityResponse;
  private ResponseEntity<UserDto> createResidentResponse;

  private TestRestTemplate getAuthenticatedRestTemplate() {
    return restTemplate.withBasicAuth("root", "root");
  }

  @BeforeEach
  void setup() {
    EntityDto entityDto = buildEntityDto();

    createEntityResponse =
        getAuthenticatedRestTemplate().postForEntity("/entities", entityDto, EntityDto.class);

    UserDto userDto = buildUserDto(createEntityResponse.getBody(), UserType.RESIDENT);
    createResidentResponse =
        getAuthenticatedRestTemplate().postForEntity("/users", userDto, UserDto.class);
  }

  @Test
  void testCreateParking() {
    ParkingDto parkingDto = buildParkingDto(createResidentResponse.getBody());

    ResponseEntity<ParkingDto> createParkingResponse =
        getAuthenticatedRestTemplate().postForEntity("/parking", parkingDto, ParkingDto.class);

    assertEquals(HttpStatus.OK, createParkingResponse.getStatusCode());
    assertEquals("001", Objects.requireNonNull(createParkingResponse.getBody()).getIdentifier());
  }

  @Test
  void testPatchUpdate() {
    ParkingDto parkingDto = buildParkingDto(createResidentResponse.getBody());

    ResponseEntity<ParkingDto> createParkingResponse =
        getAuthenticatedRestTemplate().postForEntity("/parking", parkingDto, ParkingDto.class);

    ParkingDto createdparkingDto = createParkingResponse.getBody();
    Objects.requireNonNull(createdparkingDto).setIdentifier("Updated Name");

    ResponseEntity<ParkingDto> patchResponse =
        getAuthenticatedRestTemplate()
            .exchange(
                "/parking/" + createdparkingDto.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(createdparkingDto),
                ParkingDto.class);

    assertEquals(HttpStatus.OK, patchResponse.getStatusCode());
    assertEquals("Updated Name", Objects.requireNonNull(patchResponse.getBody()).getIdentifier());
  }

  @Test
  void testRetrieveUserByUser() {
    ParkingDto parkingDto = buildParkingDto(createResidentResponse.getBody());

    ResponseEntity<ParkingDto> createParkingResponse =
        getAuthenticatedRestTemplate().postForEntity("/parking", parkingDto, ParkingDto.class);

    // Act: Retrieve all entities
    ResponseEntity<ParkingDto[]> getResponse =
        getAuthenticatedRestTemplate()
            .getForEntity(
                "/parking/find-by-user/"
                    + Objects.requireNonNull(createParkingResponse.getBody()).getId(),
                ParkingDto[].class);

    // Assert: Verify retrieval
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(1, Objects.requireNonNull(getResponse.getBody()).length);
    assertEquals("001", getResponse.getBody()[0].getIdentifier());
  }

  @Test
  void testRetrieveUserByEntity() {
    ParkingDto parkingDto = buildParkingDto(createResidentResponse.getBody());

    ResponseEntity<ParkingDto> createParkingResponse =
        getAuthenticatedRestTemplate().postForEntity("/parking", parkingDto, ParkingDto.class);

    // Act: Retrieve all entities
    ResponseEntity<ParkingDto[]> getResponse =
        getAuthenticatedRestTemplate()
            .getForEntity(
                "/parking/find-by-entity/"
                    + Objects.requireNonNull(createEntityResponse.getBody()).getId(),
                ParkingDto[].class);

    // Assert: Verify retrieval
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(1, Objects.requireNonNull(getResponse.getBody()).length);
    assertEquals("001", getResponse.getBody()[0].getIdentifier());
  }
}
