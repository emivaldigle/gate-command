package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.business.UserService;
import com.visp.gate_command.controller.UserController;
import com.visp.gate_command.domain.dto.UserDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@Loggable
public class UserControllerImpl implements UserController {

  private final UserService userService;

  @Override
  public ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto) {
    return ResponseEntity.ok(userService.create(userDto));
  }

  @Override
  public ResponseEntity<Void> batchSave(
      @RequestParam("file") MultipartFile file, @PathVariable Long entityId) {
    userService.loadUsersWithFile(file, entityId);
    return ResponseEntity.accepted().build();
  }

  @Override
  public ResponseEntity<Void> batchDelete(
      @RequestParam("file") MultipartFile file, @PathVariable Long entityId) {
    userService.deactivateUsersWithFile(file, entityId);
    return ResponseEntity.accepted().build();
  }
}
