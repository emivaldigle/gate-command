package com.visp.gate_command.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.visp.gate_command.domain.enums.UserType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessAuthorizationResponse {
  private boolean isAuthorized;
  private UserType userType;
  private String parkingIdentifier;

  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private LocalDateTime timestamp;
}
