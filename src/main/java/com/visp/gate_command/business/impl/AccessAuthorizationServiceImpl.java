package com.visp.gate_command.business.impl;

import com.visp.gate_command.business.AccessAuthorizationService;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.business.VehicleService;
import com.visp.gate_command.domain.dto.AccessAuthorizationResponse;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.enums.UserType;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessAuthorizationServiceImpl implements AccessAuthorizationService {
  private final VehicleService vehicleService;
  private final ParkingService parkingService;

  @Override
  public AccessAuthorizationResponse isVehicleAuthorized(UUID entityId, String licensePlate) {
    return vehicleService
        .findByLicensePlateAndEntityId(licensePlate, entityId)
        .map(
            vehicle -> {
              Optional<ParkingDto> currentParking =
                  parkingService.findByCurrentLicensePlate(licensePlate);
              final var parking = parkingService.getAllByUser(vehicle.getUser().getId());
              boolean isAuthorized =
                  currentParking.isPresent() || parking.stream().anyMatch(ParkingDto::getAvailable);
              final var identifier =
                  parking.stream().map(ParkingDto::getIdentifier).findFirst().orElse(null);

              return buildResponse(isAuthorized, vehicle.getUser().getType(), identifier);
            })
        .orElse(buildResponse(false, UserType.EXTERNAL, null));
  }

  private AccessAuthorizationResponse buildResponse(
      boolean isAuthorized, UserType userType, String parkingIdentifier) {
    return AccessAuthorizationResponse.builder()
        .isAuthorized(isAuthorized)
        .timestamp(LocalDateTime.now())
        .userType(userType)
        .parkingIdentifier(parkingIdentifier)
        .build();
  }
}
