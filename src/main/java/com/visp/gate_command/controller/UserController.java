package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.UUID;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User Management", description = "Endpoints for managing users")
@RequestMapping("/users")
public interface UserController {
  @Operation(
      summary = "Save a new user",
      description = "Creates and saves a new user in the system",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "User saved successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping
  ResponseEntity<UserDto> save(@RequestBody @Valid UserDto userDto);

  @Operation(
      summary = "Save a new user",
      description = "Save a batch of users from given cvs, xlsx file to given entityId",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "File posted successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = UserDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping(value = "/batch-save/{entityId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<Void> batchSave(
      @RequestParam("file") MultipartFile file, @PathVariable UUID entityId);

  @Operation(
      summary = "Remove given users from system",
      description = "Delete all users found in cvs, xlsx file",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "File posted successfully",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping(value = "/batch-delete/{entityId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  ResponseEntity<Void> batchDelete(
      @RequestParam("file") MultipartFile file, @PathVariable UUID entityId);

  @Operation(
      summary = "Update an existing user",
      description = "Updates an existing user with partial or full data",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "User updated successfully",
            content = @Content),
        @ApiResponse(responseCode = "404", description = "User not found", content = @Content)
      })
  @PatchMapping("/{id}")
  ResponseEntity<UserDto> update(@RequestBody UserDto userDto, @PathVariable UUID id);
}
