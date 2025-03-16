package com.visp.gate_command.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visp.gate_command.domain.enums.UserType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
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

  @NotEmpty
  @Size(min = 9, max = 15)
  private String document;

  @NotEmpty
  @Size(min = 2, max = 20)
  private String name;

  @NotEmpty
  @Size(min = 2, max = 40)
  private String lastName;

  private String phoneNumber;

  @Email private String email;

  private String password;

  private String unit;

  private UserType type;

  private EntityDto entity;

  private Boolean hasAssignedParking;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime visitDateTime;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime createdAt;
}
