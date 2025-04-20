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
import com.visp.gate_command.exception.UserAlreadyExistException;
import com.visp.gate_command.mapper.UserMapper;
import com.visp.gate_command.messaging.MqttPublishService;
import com.visp.gate_command.repository.UserRepository;
import com.visp.gate_command.util.Encryptor;
import com.visp.gate_command.util.FileProcessor;
import jakarta.transaction.Transactional;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Loggable(level = "DEBUG")
public class UserServiceImpl implements UserService {
  private final UserRepository repository;
  private final EntityService entityService;
  private final UserMapper userMapper;
  private final List<UserType> ALLOWED_USER_TYPES =
      List.of(UserType.ADMINISTRATOR, UserType.RESIDENT, UserType.CONCIERGE, UserType.VISIT);
  private final MqttPublishService mqttPublishService;
  private static final String TOPIC_USER_CREATE = "user/%s/create";
  private static final String TOPIC_USER_UPDATE = "user/%s/update";
  private static final String TOPIC_USER_BATCH_CREATE = "user/%s/batch-create";
  private static final String TOPIC_USER_BATCH_DELETE = "user/%s/batch-delete";

  @Override
  public UserDto create(UserDto userDto) {
    Optional<User> userByEmail = repository.findByEmail(userDto.getEmail());
    if (userByEmail.isPresent()) {
      throw new UserAlreadyExistException("User email already exists");
    }
    userDto.setId(UUID.randomUUID());
    userDto.setCreatedAt(LocalDateTime.now());
    userDto.setLastUpdatedAt(LocalDateTime.now());
    final var optionalUserDto =
        Optional.of(userDto)
            .filter(dto -> ALLOWED_USER_TYPES.contains(dto.getType()))
            .map(dto -> repository.save(userMapper.toEntity(dto)))
            .map(userMapper::toDto)
            .orElseThrow(
                () ->
                    new UnsupportedOperationException(
                        String.format(
                            "Unable to create user with given profile %s", userDto.getType())));
    mqttPublishService.publishMessage(
        String.format(TOPIC_USER_CREATE, userDto.getEntity().getId()), optionalUserDto);
    return optionalUserDto;
  }

  @Override
  public UserDto update(UserDto userDto, UUID userId) {
    Optional<User> optionalUser = repository.findById(userId);
    if (optionalUser.isEmpty()) {
      throw new NotFoundException("user not found");
    }
    userDto.setLastUpdatedAt(LocalDateTime.now());
    final var updatedUser = userMapper.toDto(repository.save(userMapper.toEntity(userDto)));
    mqttPublishService.publishMessage(
        String.format(TOPIC_USER_UPDATE, updatedUser.getEntity().getId()), updatedUser);
    return updatedUser;
  }

  @Override
  @Transactional
  public void loadUsersWithFile(MultipartFile file, UUID entityId) {
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
        UserDto userDto =
            UserDto.builder()
                .id(UUID.randomUUID())
                .document(row[0])
                .name(row[1])
                .lastName(row[2])
                .email(row[3])
                .phoneNumber(row[4])
                .unit(row[5])
                .hasAssignedParking(Boolean.valueOf(row[6]))
                .password(Encryptor.getEncryptedPassword(DEFAULT_PASSWORD))
                .type(UserType.RESIDENT)
                .entity(optionalEntityDto.get())
                .createdAt(LocalDateTime.now())
                .lastUpdatedAt(LocalDateTime.now())
                .build();
        repository.save(userMapper.toEntity(userDto));
      }
    } catch (Exception ex) {
      throw new FileProcessingException(
          String.format(
              "Massive activation, unable to process file %s for entity id %s",
              file.getName(), entityId));
    }
  }

  @Override
  @Transactional
  public void deactivateUsersWithFile(MultipartFile file, UUID entityId) {
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

  @Override
  public Optional<UserDto> findByUserId(UUID userId) {
    return repository.findById(userId).map(userMapper::toDto);
  }
}
