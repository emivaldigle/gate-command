package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.ParkingDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Parking Management", description = "Endpoints for managing parkings")
@RequestMapping("/parking")
public interface ParkingController {

  @Operation(
      summary = "Create a new parking",
      description = "Creates and saves a new parking in the system",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "parking created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ParkingDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping
  ResponseEntity<ParkingDto> create(@RequestBody ParkingDto parkingDto);

  @Operation(
      summary = "Update an existing parking",
      description = "Updates an existing parking by its ID",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "parking updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ParkingDto.class))),
        @ApiResponse(responseCode = "404", description = "parking not found", content = @Content)
      })
  @PatchMapping("/{id}")
  ResponseEntity<ParkingDto> update(@PathVariable UUID id, @RequestBody ParkingDto parkingDto);

  @Operation(
      summary = "Get all parkings by entity",
      description = "Retrieves all parkings associated with a specific entity",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "parkings retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ParkingDto.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
      })
  @GetMapping("/find-by-entity/{entityId}")
  ResponseEntity<List<ParkingDto>> getAllByEntity(@PathVariable UUID entityId);

  @Operation(
      summary = "Get all parkings by entity",
      description =
          "Retrieves all parkings associated with a specific entity and date greater than provided",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "parkings retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ParkingDto.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
      })
  @GetMapping("/find-by-entity/{entityId}/date")
  ResponseEntity<List<ParkingDto>> findByEntityAndGreaterThanDate(
      @PathVariable UUID entityId, @RequestParam String date);

  @Operation(
      summary = "Get all parkings by user",
      description = "Retrieves all parkings associated with a specific user",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "parkings retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ParkingDto.class, type = "array"))),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  @GetMapping("/find-by-user/{userId}")
  ResponseEntity<List<ParkingDto>> getAllByUser(@PathVariable UUID userId);

  @Operation(
      summary = "Seed parking within an entity",
      description = "Generates empty parking within an entity",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "parking created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = ParkingDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping("/seed/{entityId}")
  ResponseEntity<Void> seed(@PathVariable UUID entityId);

  @Operation(
      summary = "Delete an parking by ID",
      description = "Deletes an parking from the system based on its ID",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "Parking deleted successfully",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Parking not found", content = @Content)
      })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id);
}
