package com.visp.gate_command.domain.dto;

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
  private LocalDateTime timestamp;
}
