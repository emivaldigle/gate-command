package com.visp.gate_command.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.domain.dto.ParkingSummaryDto;
import com.visp.gate_command.domain.entity.Parking;
import com.visp.gate_command.mapper.ParkingMapper;
import com.visp.gate_command.messaging.MqttSubscriberService;
import com.visp.gate_command.repository.ParkingRepository;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttParkingHandler implements MqttSubscriberService {
  private static final Logger log = LoggerFactory.getLogger(MqttParkingHandler.class);
  private final ParkingRepository parkingRepository;
  private final ObjectMapper objectMapper;
  private final ParkingMapper parkingMapper;

  @Override
  public void messageArrived(String topic, MqttMessage message) {
    log.info("Message arrived on topic {} with message: \n {}", topic, message);
    try {
      ParkingSummaryDto parkingSummaryDto =
          objectMapper.readValue(message.getPayload(), ParkingSummaryDto.class);
      Optional<Parking> parking = parkingRepository.findByEntityIdAndIdentifier(parkingSummaryDto.getEntityId(), parkingSummaryDto.getIdentifier());

      if (parking.isPresent()) {
        parking.get().setIdentifier(parkingSummaryDto.getIdentifier());
        parking.get().setAvailable(parkingSummaryDto.getAvailable());
        parking.get().setLastUpdatedAt(parseDate(parkingSummaryDto.getLastUpdatedAt()));
        parking.get().setCurrentLicensePlate(parkingSummaryDto.getCurrentLicensePlate());
        parkingRepository.save(parking.get());
      } else {
        log.error("Parking not found");
      }
    } catch (Exception ex) {
      log.error("Unable to unpack message {}", ex.getMessage());
    }
  }

  private LocalDateTime parseDate(String date) {
    if (date == null || date.isEmpty()) {
      return null;
    }
    DateTimeFormatter[] formatters = {
      DateTimeFormatter.ISO_DATE_TIME,
      DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
      DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss")
    };
    for (DateTimeFormatter formatter : formatters) {
      try {
        return LocalDateTime.parse(date, formatter);
      } catch (Exception ignored) {
        // Intentar con el siguiente formato
      }
    }
    throw new IllegalArgumentException("Invalid date format: " + date);
  }
}
