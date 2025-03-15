package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.AccessAuthorizationResponse;

public interface AccessAuthorizationService {
  AccessAuthorizationResponse isVehicleAuthorized(String licensePlate);
}
