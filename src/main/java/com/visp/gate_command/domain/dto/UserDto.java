package com.visp.gate_command.domain.dto;

import com.visp.gate_command.domain.enums.UserType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {

  private Long id;

  private String document;

  private String name;

  private String lastName;

  private String phoneNumber;

  private String email;

  private String password;

  private UserType type;

  private EntityDto entity;

  private LocalDateTime createdAt;
}
