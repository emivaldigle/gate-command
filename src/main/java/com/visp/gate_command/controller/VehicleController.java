package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.VehicleDto;
import com.visp.gate_command.domain.dto.VehicleSummaryDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Vehicle Management", description = "Endpoints for managing vehicles")
@RequestMapping("/vehicles")
public interface VehicleController {

  @Operation(
      summary = "Create a new vehicle",
      description = "Creates and saves a new vehicle in the system",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehicle created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VehicleDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping
  ResponseEntity<VehicleDto> create(@RequestBody VehicleDto vehicleDto);

  @Operation(
      summary = "Update an existing vehicle",
      description = "Updates an existing vehicle by its ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehicle updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VehicleDto.class))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
      })
  @PatchMapping("/{id}")
  ResponseEntity<VehicleDto> update(@PathVariable UUID id, @RequestBody VehicleDto vehicleDto);

  @Operation(
      summary = "Delete a vehicle",
      description = "Deletes a vehicle by its ID",
      responses = {
        @ApiResponse(responseCode = "204", description = "Vehicle deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
      })
  @DeleteMapping("/userId")
  ResponseEntity<Void> delete(@PathVariable UUID userId, @RequestParam UUID entityId);

  @Operation(
      summary = "Get all vehicles by user",
      description = "Retrieves all vehicles associated with a specific user",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehicles retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VehicleDto.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
      })
  @GetMapping("/find-by-user/{userId}")
  ResponseEntity<List<VehicleDto>> getByUserId(@PathVariable UUID userId);

  @Operation(
      summary = "Get all vehicles by entity",
      description = "Retrieves all vehicles associated with a specific entity",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehicles retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VehicleDto.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
      })
  @GetMapping("/find-by-entity/{entityId}")
  ResponseEntity<List<VehicleDto>> getAllByEntity(@PathVariable UUID entityId);

  @Operation(
      summary = "Get all vehicle summaries by entity",
      description =
          "Retrieves summarized information about all vehicles associated with a specific entity",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehicle summaries retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VehicleSummaryDto.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
      })
  @GetMapping("/{entityId}/summaries")
  ResponseEntity<List<VehicleSummaryDto>> getAllSummariesByEntity(@PathVariable UUID entityId);

  @Operation(
      summary = "Get all vehicle summaries by entity",
      description =
          "Retrieves summarized information about all vehicles associated with a specific entity",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Vehicle summaries retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = VehicleSummaryDto.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "Vehicle not found", content = @Content)
      })
  @GetMapping("/find-by-plate/{entityId}")
  ResponseEntity<VehicleDto> findByPlateAndEntityId(
      @RequestParam String licensePlate, @PathVariable UUID entityId);
}
