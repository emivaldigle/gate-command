package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.UserService;
import com.visp.gate_command.controller.UserController;
import com.visp.gate_command.domain.dto.UserDto;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Loggable
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @Override
  public ResponseEntity<UserDto> save(UserDto userDto) {
    return ResponseEntity.ok(userService.create(userDto));
  }

  @Override
  public ResponseEntity<Void> batchSave(@RequestParam("file") MultipartFile file, UUID entityId) {
    userService.loadUsersWithFile(file, entityId);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<Void> batchDelete(@RequestParam("file") MultipartFile file, UUID entityId) {
    userService.deactivateUsersWithFile(file, entityId);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<UserDto> update(UserDto userDto, UUID id) {
    return ResponseEntity.ok(userService.update(userDto, id));
  }
}
