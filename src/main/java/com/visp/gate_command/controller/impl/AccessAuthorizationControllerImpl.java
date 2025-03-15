package com.visp.gate_command.controller.impl;

import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.controller.AccessAuthorizationController;
import com.visp.gate_command.domain.dto.AccessAuthorizationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Loggable
public class AccessAuthorizationControllerImpl implements AccessAuthorizationController {
  @Override
  public ResponseEntity<AccessAuthorizationResponse> isVehicleAuthorized(Long id) {
    return null;
  }
}
