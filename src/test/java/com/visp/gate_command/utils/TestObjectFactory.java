package com.visp.gate_command.utils;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.dto.PocDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.enums.PocType;
import com.visp.gate_command.domain.enums.UserType;
import com.visp.gate_command.domain.enums.VehicleType;
import java.time.LocalDateTime;

public class TestObjectFactory {
  public static EntityDto buildEntityDto() {
    return EntityDto.builder()
        .name("name")
        .type("type")
        .taxId("12345")
        .city("city")
        .address("address")
        .syncIntervalMinutes(5)
        .parkingHoursAllowed(5)
        .visitSizeLimit(40)
        .active(true)
        .contactPhone("contactPhone")
        .region("region")
        .commune("commune")
        .build();
  }

  public static PocDto buildPocDto(EntityDto entityDto, String identifier) {
    return PocDto.builder()
        .name("poc 1")
        .entity(entityDto)
        .name("Test POC")
        .type(PocType.INGRESS)
        .identifier(identifier)
        .location("location")
        .build();
  }

  public static UserDto buildUserDto(EntityDto entityDto, UserType userType) {
    return UserDto.builder()
        .document("12345678-9")
        .name("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .password("password")
        .phoneNumber("phoneNumber")
        .type(userType)
        .entity(entityDto)
        .hasAssignedParking(false)
        .unit("108")
        .build();
  }

  public static ParkingDto buildParkingDto(UserDto userDto) {
    return ParkingDto.builder()
        .identifier("001")
        .user(userDto)
        .createdAt(LocalDateTime.now())
        .available(true)
        .expirationDate(null)
        .isForVisit(true)
        .build();
  }

  public static VehicleDto buildVehicleDto(UserDto userDto) {
    return VehicleDto.builder().plate("TTWC85").vehicleType(VehicleType.CAR).user(userDto).build();
  }
}
