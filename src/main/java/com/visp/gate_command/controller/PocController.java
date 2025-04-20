package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.PocDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "POC Management", description = "Endpoints for managing POCs")
@RequestMapping("/pocs")
public interface PocController {

  @Operation(
      summary = "Create a new POC",
      description = "Creates and saves a new POC in the system",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "POC created successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PocDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping
  ResponseEntity<PocDto> create(@RequestBody PocDto pocDto);

  @Operation(
      summary = "Update an existing POC",
      description = "Updates an existing POC with the provided data",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "POC updated successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PocDto.class))),
        @ApiResponse(responseCode = "404", description = "POC not found", content = @Content)
      })
  @PatchMapping("/{id}")
  ResponseEntity<PocDto> update(@RequestBody PocDto pocDto, @PathVariable UUID id);

  @Operation(
      summary = "Delete a POC by ID",
      description = "Deletes a POC from the system based on its ID",
      responses = {
        @ApiResponse(
            responseCode = "204",
            description = "POC deleted successfully",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "POC not found", content = @Content)
      })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable UUID id);

  @Operation(
      summary = "Retrieve POCs by Entity ID",
      description = "Fetches all POCs associated with a specific entity",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "POCs retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = PocDto.class))),
        @ApiResponse(responseCode = "404", description = "No POCs found", content = @Content)
      })
  @GetMapping("/find-by-entity/{entityId}")
  ResponseEntity<List<PocDto>> retrievePocByEntity(@PathVariable UUID entityId);
}
