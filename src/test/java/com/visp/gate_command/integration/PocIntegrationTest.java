package com.visp.gate_command.integration;

import static com.visp.gate_command.utils.TestObjectFactory.buildEntityDto;
import static com.visp.gate_command.utils.TestObjectFactory.buildPocDto;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.PocDto;
import java.util.Map;
import java.util.Objects;
import java.util.stream.IntStream;
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
class PocIntegrationTest {

  @Autowired private TestRestTemplate restTemplate;
  private ResponseEntity<EntityDto> createEntityResponse;

  private TestRestTemplate getAuthenticatedRestTemplate() {
    return restTemplate.withBasicAuth("root", "root");
  }

  @BeforeEach
  void setup() {
    EntityDto entityDto = buildEntityDto();
    createEntityResponse =
        getAuthenticatedRestTemplate().postForEntity("/entities", entityDto, EntityDto.class);
  }

  @Test
  void testCreateAndGetPoc() {
    PocDto pocDto = buildPocDto(createEntityResponse.getBody(), "POC 00001");

    ResponseEntity<PocDto> createResponse =
        getAuthenticatedRestTemplate().postForEntity("/pocs", pocDto, PocDto.class);

    assertEquals(HttpStatus.OK, createResponse.getStatusCode());
    assertEquals("Test POC", Objects.requireNonNull(createResponse.getBody()).getName());
  }

  @Test
  void testPatchUpdate() {
    PocDto pocDto = buildPocDto(createEntityResponse.getBody(), "POC 00002");
    ResponseEntity<PocDto> createResponse =
        getAuthenticatedRestTemplate().postForEntity("/pocs", pocDto, PocDto.class);

    PocDto createdPocDto = createResponse.getBody();
    Objects.requireNonNull(createdPocDto).setName("Updated Name");

    ResponseEntity<PocDto> patchResponse =
        getAuthenticatedRestTemplate()
            .exchange(
                "/pocs/" + createdPocDto.getId(),
                HttpMethod.PATCH,
                new HttpEntity<>(createdPocDto),
                PocDto.class);

    assertEquals(HttpStatus.OK, patchResponse.getStatusCode());
    assertEquals("Updated Name", Objects.requireNonNull(patchResponse.getBody()).getName());
  }

  @Test
  void testRetrievePocByEntity() {
    PocDto pocDto = buildPocDto(createEntityResponse.getBody(), "POC 00003");

    IntStream.range(3, 5)
        .forEach(
            i -> {
              pocDto.setIdentifier(String.format("POC 0000%d", i));
              getAuthenticatedRestTemplate().postForEntity("/pocs", pocDto, PocDto.class);
            });

    Map<String, Long> parameterMap =
        Map.of("entityId", Objects.requireNonNull(createEntityResponse.getBody()).getId());
    ResponseEntity<PocDto[]> getResponse =
        getAuthenticatedRestTemplate()
            .getForEntity("/pocs/find-by-entity/{entityId}", PocDto[].class, parameterMap);

    assertEquals(HttpStatus.OK, getResponse.getStatusCode());
    assertEquals("POC 00003", Objects.requireNonNull(getResponse.getBody())[0].getIdentifier());
    assertEquals("POC 00004", Objects.requireNonNull(getResponse.getBody())[1].getIdentifier());
    assertEquals(2, getResponse.getBody().length);
  }
}
