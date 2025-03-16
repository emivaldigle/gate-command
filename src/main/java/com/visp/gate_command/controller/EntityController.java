package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.EntityDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Entity Management", description = "Endpoints for managing entities")
@RequestMapping("/entities")
public interface EntityController {

  @Operation(
      summary = "Save a new entity",
      description = "Creates and saves a new entity in the system",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Entity saved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EntityDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping
  ResponseEntity<EntityDto> save(@RequestBody @Valid EntityDto entityDto);

  @Operation(
      summary = "Retrieve all entities",
      description = "Fetches a list of all entities in the system",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Entities retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EntityDto.class))),
        @ApiResponse(responseCode = "204", description = "No entities found", content = @Content)
      })
  @GetMapping
  ResponseEntity<List<EntityDto>> retrieveAll();

  @Operation(
      summary = "Retrieve an entity by id",
      description = "Fetches an entity by provided id",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Entities retrieved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EntityDto.class))),
        @ApiResponse(responseCode = "404", description = "No entities found", content = @Content)
      })
  @GetMapping("/{id}")
  ResponseEntity<EntityDto> findById(@PathVariable Long id);

  @Operation(
      summary = "Delete an entity by ID",
      description = "Deletes an entity from the system based on its ID",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "Entity deleted successfully",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
      })
  @DeleteMapping("/{id}")
  ResponseEntity<Void> delete(@PathVariable Long id);

  @Operation(
      summary = "Update an existing entity",
      description = "Updates an existing entity with partial or full data",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "Entity updated successfully",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "Entity not found", content = @Content)
      })
  @PatchMapping("/{id}")
  ResponseEntity<EntityDto> update(@RequestBody EntityDto entityDto, @PathVariable Long id);
}
