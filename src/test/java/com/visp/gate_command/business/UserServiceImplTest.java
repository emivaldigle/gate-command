package com.visp.gate_command.business;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.visp.gate_command.business.impl.UserServiceImpl;
import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.entity.User;
import com.visp.gate_command.domain.enums.UserType;
import com.visp.gate_command.mapper.EntityMapper;
import com.visp.gate_command.mapper.UserMapper;
import com.visp.gate_command.repository.UserRepository;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Optional;
import java.util.UUID;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

  @Mock private UserRepository repository;

  @Mock private EntityService entityService;

  @Mock private UserMapper userMapper;

  @Mock private EntityMapper entityMapper;

  @InjectMocks private UserServiceImpl userService;

  @Test
  void testCreate() {
    // Arrange
    UserDto userDto = new UserDto();
    userDto.setEmail("test@example.com");
    userDto.setType(UserType.ADMINISTRATOR);
    UUID id = UUID.randomUUID();
    User user = new User();
    user.setId(id);
    user.setEmail("test@example.com");

    when(repository.findByEmail(userDto.getEmail())).thenReturn(Optional.empty());
    when(userMapper.toEntity(userDto)).thenReturn(user);
    when(repository.save(user)).thenReturn(user);
    when(userMapper.toDto(user)).thenReturn(userDto);

    // Act
    UserDto result = userService.create(userDto);

    // Assert
    assertNotNull(result);
    assertEquals("test@example.com", result.getEmail());
    verify(repository, times(1)).save(any(User.class));
  }

  @Test
  void testLoadUsersWithCSVFile() throws Exception {
    // Arrange
    String csvContent = "123,John,Doe,john@example.com,+123456789, 1101, true";
    InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

    MultipartFile file = mock(MultipartFile.class);
    when(file.getInputStream()).thenReturn(inputStream);
    when(file.getContentType()).thenReturn("text/csv");
    UUID entityId = UUID.randomUUID();
    EntityDto entityDto = new EntityDto();
    entityDto.setId(entityId);

    when(entityService.findById(entityId)).thenReturn(Optional.of(entityDto));
    when(userMapper.toEntity(any())).thenReturn(new User());

    // Act
    userService.loadUsersWithFile(file, entityId);

    // Assert
    verify(repository, times(1)).save(any(User.class));
  }

  @Test
  void testLoadUsersWithXLSXFile() throws Exception {
    // Arrange
    UUID entityId = UUID.randomUUID();
    byte[] xlsxContent = createSampleXLSXContent();
    InputStream inputStream = new ByteArrayInputStream(xlsxContent);

    MultipartFile file = mock(MultipartFile.class);
    when(file.getInputStream()).thenReturn(inputStream);
    when(file.getContentType()).thenReturn("application/xlsx");

    EntityDto entityDto = new EntityDto();
    entityDto.setId(entityId);

    when(entityService.findById(entityId)).thenReturn(Optional.of(entityDto));
    when(userMapper.toEntity(any())).thenReturn(new User());

    // Act
    userService.loadUsersWithFile(file, entityId);

    // Assert
    verify(repository, times(1)).save(any(User.class));
  }

  @Test
  void testDeactivateUsersWithCSVFile() throws Exception {
    // Arrange
    UUID entityId = UUID.randomUUID();
    String csvContent = "123";
    InputStream inputStream = new ByteArrayInputStream(csvContent.getBytes());

    MultipartFile file = mock(MultipartFile.class);
    when(file.getInputStream()).thenReturn(inputStream);
    when(file.getContentType()).thenReturn("text/csv");

    User user = new User();
    user.setDocument("123");

    when(repository.findByDocument("123")).thenReturn(Optional.of(user));

    // Act
    userService.deactivateUsersWithFile(file, entityId);

    // Assert
    verify(repository, times(1)).delete(any(User.class));
  }

  private byte[] createSampleXLSXContent() throws Exception {
    try (Workbook workbook = new XSSFWorkbook()) {
      Sheet sheet = workbook.createSheet("Users");
      Row row = sheet.createRow(0);
      row.createCell(0).setCellValue("123");
      row.createCell(1).setCellValue("John");
      row.createCell(2).setCellValue("Doe");
      row.createCell(3).setCellValue("john@example.com");
      row.createCell(4).setCellValue("+123456789");
      row.createCell(5).setCellValue("1101");
      row.createCell(6).setCellValue("true");
      ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
      workbook.write(outputStream);
      return outputStream.toByteArray();
    }
  }
}
