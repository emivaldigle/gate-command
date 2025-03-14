package com.visp.gate_command.utils;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.PocDto;
import com.visp.gate_command.domain.dto.UserDto;
import com.visp.gate_command.domain.enums.PocType;
import com.visp.gate_command.domain.enums.UserType;

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

  public static UserDto buildUserDto(EntityDto entityDto) {
    return UserDto.builder()
        .document("12345678-9")
        .name("John")
        .lastName("Doe")
        .email("john.doe@example.com")
        .password("password")
        .phoneNumber("phoneNumber")
        .type(UserType.ADMINISTRATOR)
        .entity(entityDto)
        .build();
  }
}
