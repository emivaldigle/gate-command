package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.UserDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
  UserDto create(UserDto userDto);

  void loadUsersWithFile(MultipartFile fileName, Long entityId);

  void deactivateUsersWithFile(MultipartFile fileName, Long entityId);
}
