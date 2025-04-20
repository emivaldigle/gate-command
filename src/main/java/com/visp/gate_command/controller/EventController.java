package com.visp.gate_command.controller;

import com.visp.gate_command.domain.dto.EventDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.UUID;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Tag(name = "Event Management", description = "Endpoints for managing events")
@RequestMapping("/events")
public interface EventController {

  @Operation(
      summary = "Obtain last event with entity and license plate",
      description = "Obtain last event with entity and license plate",
      responses = {
        @ApiResponse(
            responseCode = "200",
            description = "Event obtained successfully",
            content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "400", description = "Invalid input data", content = @Content),
      })
  @GetMapping("/entity/{entityId}/plate/{licensePlate}")
  ResponseEntity<EventDto> findLastEventByEntityAndPlate(
      @PathVariable UUID entityId, @PathVariable String licensePlate);
}
