package com.visp.gate_command.business.impl;

import com.visp.gate_command.business.AccessAuthorizationService;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.business.VehicleService;
import com.visp.gate_command.domain.dto.AccessAuthorizationResponse;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.enums.UserType;
import java.time.LocalDateTime;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccessAuthorizationServiceImpl implements AccessAuthorizationService {
  private final VehicleService vehicleService;
  private final ParkingService parkingService;

  @Override
  public AccessAuthorizationResponse isVehicleAuthorized(String licensePlate) {
    return vehicleService
        .findByLicensePlate(licensePlate)
        .map(
            vehicle -> {
              Optional<ParkingDto> currentParking =  parkingService.findByCurrentLicensePlate(licensePlate);
              boolean isAuthorized =
                      currentParking.isPresent() ||
                  parkingService.getAllByUser(vehicle.getUser().getId()).stream()
                      .anyMatch(ParkingDto::getAvailable);

              return buildResponse(isAuthorized, vehicle.getUser().getType());
            })
        .orElse(buildResponse(false, null));
  }

  private AccessAuthorizationResponse buildResponse(boolean isAuthorized, UserType userType) {
    return AccessAuthorizationResponse.builder()
        .isAuthorized(isAuthorized)
        .timestamp(LocalDateTime.now())
        .userType(userType)
        .build();
  }
}
