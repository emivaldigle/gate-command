package com.visp.gate_command.domain.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ResponseDto {
  private String code;
  private String message;
}
