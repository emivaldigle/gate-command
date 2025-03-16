package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.AccessAuthorizationResponse;
import com.visp.gate_command.domain.dto.VehicleDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Access Management", description = "Endpoints for managing vehicle access")
@RequestMapping("/access")
public interface AccessAuthorizationController {

  @Operation(
      summary = "Validates whether a vehicle is allowed to access or not",
      description = "Validates whether a vehicle is allowed to access or not",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehicle validated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VehicleDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      })
  @GetMapping("/{id}")
  ResponseEntity<AccessAuthorizationResponse> isVehicleAuthorized(@PathVariable Long id);
}
