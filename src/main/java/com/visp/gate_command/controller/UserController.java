package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.EntityDto;
import com.visp.gate_command.domain.dto.UserDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "User Management", description = "Endpoints for managing users")
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
  @PostMapping("/users/save")
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
  @PostMapping("/users/batch-save/{entityId}")
  ResponseEntity<Void> batchSAve(
      @RequestBody MultipartFile multipartFile, @PathVariable Long entityId);

  @Operation(
      summary = "Remove given users from system",
      description = "Delete all users found in cvs, xlsx file",
      responses = {
        @ApiResponse(
            responseCode = "202",
            description = "File posted successfully",
            content =
                @Content(
                    mediaType = "application/json",
                    schema = @Schema(implementation = EntityDto.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content)
      })
  @PostMapping("/users/batch-delete/{entityId}")
  ResponseEntity<Void> batchDelete(
      @RequestBody MultipartFile multipartFile, @PathVariable Long entityId);
}
