package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.UserDto;
import java.util.Optional;
import java.util.UUID;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
  UserDto create(UserDto userDto);

  UserDto update(UserDto userDto, UUID userId);

  void loadUsersWithFile(MultipartFile fileName, UUID entityId);

  void deactivateUsersWithFile(MultipartFile fileName, UUID entityId);

  Optional<UserDto> findByUserId(UUID userId);
}
