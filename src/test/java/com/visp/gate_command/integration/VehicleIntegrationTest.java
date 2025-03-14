package com.visp.gate_command.integration;

import static com.visp.gate_command.utils.TestObjectFactory.buildEntityDto;
import static com.visp.gate_command.utils.TestObjectFactory.buildUserDto;
import static com.visp.gate_command.utils.TestObjectFactory.buildVehicleDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.dto.VehicleDto;
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
class VehicleIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;
  private ResponseEntity<EntityDto> createEntityResponse;
  private ResponseEntity<UserDto> createUserResponse;

  private TestRestTemplate getAuthenticatedRestTemplate() {
    return restTemplate.withBasicAuth("root", "root");
  }

  @BeforeEach
  void setup() {
    // Crear una entidad
    EntityDto entityDto = buildEntityDto();
    createEntityResponse =
        getAuthenticatedRestTemplate().postForEntity("/entities", entityDto, EntityDto.class);

    // Crear un usuario asociado a la entidad
    UserDto userDto = buildUserDto(createEntityResponse.getBody(), UserType.RESIDENT);
    createUserResponse =
        getAuthenticatedRestTemplate().postForEntity("/users", userDto, UserDto.class);
  }

  @Test
  void testCreateVehicle() {
    // Arrange: Crear un vehículo asociado al usuario
    VehicleDto vehicleDto = buildVehicleDto(createUserResponse.getBody());

    // Act: Crear el vehículo
    ResponseEntity<VehicleDto> createVehicleResponse =
        getAuthenticatedRestTemplate().postForEntity("/vehicles", vehicleDto, VehicleDto.class);

    // Assert: Verificar la creación
    assertEquals(HttpStatus.OK, createVehicleResponse.getStatusCode());
    assertEquals("TTWC85", Objects.requireNonNull(createVehicleResponse.getBody()).getPlate());
  }

  @Test
  void testUpdateVehicle() {
    // Arrange: Crear un vehículo
    VehicleDto vehicleDto = buildVehicleDto(createUserResponse.getBody());
    ResponseEntity<VehicleDto> createVehicleResponse =
        getAuthenticatedRestTemplate().postForEntity("/vehicles", vehicleDto, VehicleDto.class);

    // Actualizar los datos del vehículo
    VehicleDto createdVehicle = createVehicleResponse.getBody();
    Objects.requireNonNull(createdVehicle).setPlate("XYZ789");

    // Act: Actualizar el vehículo
    ResponseEntity<VehicleDto> updateResponse =
        getAuthenticatedRestTemplate()
            .exchange(
                "/vehicles/" + createdVehicle.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(createdVehicle),
                VehicleDto.class);

    // Assert: Verificar la actualización
    assertEquals(HttpStatus.OK, updateResponse.getStatusCode());
    assertEquals("XYZ789", Objects.requireNonNull(updateResponse.getBody()).getPlate());
  }

  @Test
  void testDeleteVehicle() {
    // Arrange: Crear un vehículo
    VehicleDto vehicleDto = buildVehicleDto(createUserResponse.getBody());
    ResponseEntity<VehicleDto> createVehicleResponse =
        getAuthenticatedRestTemplate().postForEntity("/vehicles", vehicleDto, VehicleDto.class);

    // Assert: Verificar que el vehículo fue eliminado
    ResponseEntity<Void> deleteResponse =
        getAuthenticatedRestTemplate()
            .exchange(
                "/vehicles/" + createVehicleResponse.getBody().getId(),
                HttpMethod.DELETE,
                null,
                Void.class);
    assertEquals(HttpStatus.NO_CONTENT, deleteResponse.getStatusCode());
  }

  @Test
  void testGetVehiclesByUser() {
    // Arrange: Crear un vehículo asociado al usuario
    VehicleDto vehicleDto = buildVehicleDto(createUserResponse.getBody());
    getAuthenticatedRestTemplate().postForEntity("/vehicles", vehicleDto, VehicleDto.class);

    // Act: Obtener todos los vehículos del usuario
    ResponseEntity<VehicleDto[]> getResponse =
        getAuthenticatedRestTemplate()
            .getForEntity(
                "/vehicles/user/" + Objects.requireNonNull(createUserResponse.getBody()).getId(),
                VehicleDto[].class);

    // Assert: Verificar la recuperación
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(1, Objects.requireNonNull(getResponse.getBody()).length);
    assertEquals("TTWC85", getResponse.getBody()[0].getPlate());
  }

  @Test
  void testGetAllVehiclesByEntity() {
    // Arrange: Crear un vehículo asociado al usuario (y por ende a la entidad)
    VehicleDto vehicleDto = buildVehicleDto(createUserResponse.getBody());
    getAuthenticatedRestTemplate().postForEntity("/vehicles", vehicleDto, VehicleDto.class);

    // Act: Obtener todos los vehículos de la entidad
    ResponseEntity<VehicleDto[]> getResponse =
        getAuthenticatedRestTemplate()
            .getForEntity(
                "/vehicles/entity/"
                    + Objects.requireNonNull(createEntityResponse.getBody()).getId(),
                VehicleDto[].class);

    // Assert: Verificar la recuperación
    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals(1, Objects.requireNonNull(getResponse.getBody()).length);
    assertEquals("TTWC85", getResponse.getBody()[0].getPlate());
  }
}
