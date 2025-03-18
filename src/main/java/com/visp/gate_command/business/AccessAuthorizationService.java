package com.visp.gate_command.business;

import com.visp.gate_command.domain.dto.AccessAuthorizationResponse;
import java.util.UUID;

public interface AccessAuthorizationService {
  AccessAuthorizationResponse isVehicleAuthorized(UUID entityId, String licensePlate);
}
