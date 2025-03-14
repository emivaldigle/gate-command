package com.visp.gate_command.integration;

import static com.visp.gate_command.utils.TestObjectFactory.buildEntityDto;
import static com.visp.gate_command.utils.TestObjectFactory.buildUserDto;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.domain.dto.EntityDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@ActiveProfiles("intTest")
class UserIntegrationTest {

  @Autowired private MockMvc mockMvc;
  @Autowired private ObjectMapper objectMapper;

  private EntityDto entityDto;
  private static final String AUTH_HEADER =
      "Basic " + java.util.Base64.getEncoder().encodeToString("root:root".getBytes());

  @BeforeEach
  void setup() throws Exception {
    EntityDto entityDto = buildEntityDto();

    String responseJson =
        mockMvc
            .perform(
                post("/entities")
                    .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(entityDto)))
            .andExpect(status().isOk())
            .andReturn()
            .getResponse()
            .getContentAsString();

    this.entityDto = objectMapper.readValue(responseJson, EntityDto.class);
  }

  @Test
  void testSaveUser() throws Exception {
    mockMvc
        .perform(
            post("/users")
                .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildUserDto(entityDto))))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name").value("John"))
        .andExpect(jsonPath("$.email").value("john.doe@example.com"));
  }

  @Test
  void testBatchSaveWithCustomHeaders() throws Exception {
    // Arrange
    String csvContent = "123,John,Doe,john@example.com,+123456789";
    MockMultipartFile file =
        new MockMultipartFile("file", "test.csv", "csv", csvContent.getBytes());

    // Act & Assert
    mockMvc
        .perform(
            multipart("/users/batch-save/" + entityDto.getId())
                .file(file)
                .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                .contentType(MediaType.MULTIPART_FORM_DATA))
        .andExpect(status().isAccepted());
  }

  @Test
  void testBatchDelete() throws Exception {
    mockMvc
        .perform(
            post("/users")
                .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(buildUserDto(entityDto))))
        .andExpect(status().isOk());
    // Arrange
    MockMultipartFile file =
        new MockMultipartFile("file", "test.csv", "text/csv", "12345678-9".getBytes());

    // Act & Assert
    mockMvc
        .perform(
            multipart("/users/batch-delete/1")
                .file(file)
                .header(HttpHeaders.AUTHORIZATION, AUTH_HEADER))
        .andExpect(status().isAccepted());
  }
}
