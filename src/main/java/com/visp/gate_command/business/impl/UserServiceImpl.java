package com.visp.gate_command.business.impl;

import static org.apache.poi.poifs.crypt.Decryptor.DEFAULT_PASSWORD;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.EntityService;
import com.visp.gate_command.business.UserService;
import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.entity.User;
import com.visp.gate_command.domain.enums.UserType;
import com.visp.gate_command.exception.FileProcessingException;
import com.visp.gate_command.exception.NotFoundException;
import com.visp.gate_command.mapper.EntityMapper;
import com.visp.gate_command.mapper.UserMapper;
import com.visp.gate_command.repository.UserRepository;
import com.visp.gate_command.util.Encryptor;
import com.visp.gate_command.util.FileProcessor;
import java.io.InputStream;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Loggable
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final EntityService entityService;
  private final UserMapper userMapper;
  private final EntityMapper entityMapper;
  private final List<UserType> ALLOWED_USER_TYPES =
      List.of(UserType.ADMINISTRATOR, UserType.RESIDENT, UserType.CONCIERGE);

  @Override
  public UserDto create(UserDto userDto) {
    return Optional.of(userDto)
        .filter(dto -> ALLOWED_USER_TYPES.contains(dto.getType()))
        .map(dto -> repository.save(userMapper.toEntity(dto)))
        .map(userMapper::toDto)
        .orElseThrow(
            () ->
                new UnsupportedOperationException(
                    String.format(
                        "Unable to create user with given profile %s", userDto.getType())));
  }

  public void loadUsersWithFile(MultipartFile file, Long entityId) {
    Optional<EntityDto> optionalEntityDto = entityService.findById(entityId);
    if (optionalEntityDto.isEmpty()) {
      throw new NotFoundException(String.format("Entity not found %s", entityId));
    }
    try {
      InputStream inputStream = file.getInputStream();
      List<String[]> rows;

      if (Objects.requireNonNull(file.getContentType()).contains("csv")) {
        rows = FileProcessor.processCSV(inputStream);
      } else if (file.getContentType().contains("xlsx")) {
        rows = FileProcessor.processXLSX(inputStream);
      } else {
        throw new IllegalArgumentException("Unsupported file type");
      }

      for (String[] row : rows) {
        User user = new User();
        user.setDocument(row[0]);
        user.setName(row[1]);
        user.setLastName(row[2]);
        user.setEmail(row[3]);
        user.setPhoneNumber(row[4]);
        user.setPassword(Encryptor.getEncryptedPassword(DEFAULT_PASSWORD));
        user.setType(UserType.RESIDENT);
        user.setEntity(entityMapper.toEntity(optionalEntityDto.get()));
      }
    } catch (Exception ex) {
      throw new FileProcessingException(
          String.format(
              "Massive activation, unable to process file %s for entity id %s",
              file.getName(), entityId));
    }
  }

  @Override
  public void deactivateUsersWithFile(MultipartFile file, Long entityId) {
    try {
      InputStream inputStream = file.getInputStream();
      List<String[]> rows;

      if (Objects.requireNonNull(file.getContentType()).contains("csv")) {
        rows = FileProcessor.processCSV(inputStream);
      } else if (file.getContentType().contains("xlsx")) {
        rows = FileProcessor.processXLSX(inputStream);
      } else {
        throw new IllegalArgumentException("Unsupported file type");
      }

      for (String[] row : rows) {
        String document = row[0];
        User user =
            repository
                .findByDocument(document)
                .orElseThrow(() -> new RuntimeException("User not found: " + document));

        repository.delete(user);
      }
    } catch (Exception ex) {
      throw new FileProcessingException(
          String.format(
              "Massive deactivation, unable to process file %s for entity id %s",
              file.getName(), entityId));
    }
  }
}
