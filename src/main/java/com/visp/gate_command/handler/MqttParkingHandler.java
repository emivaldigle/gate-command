package com.visp.gate_command.handler;

import static com.visp.gate_command.util.DateUtils.parseDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.business.ParkingService;
import com.visp.gate_command.domain.dto.ParkingDto;
import com.visp.gate_command.domain.dto.ParkingSummaryDto;
import com.visp.gate_command.messaging.MqttSubscriberService;
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
  private final ParkingService parkingService;
  private final ObjectMapper objectMapper;

  @Override
  public void messageArrived(String topic, MqttMessage message) {
    log.info("Message arrived on topic {} with message: \n {}", topic, message);
    try {
      ParkingSummaryDto parkingSummaryDto =
          objectMapper.readValue(message.getPayload(), ParkingSummaryDto.class);
      Optional<ParkingDto> parking =
          parkingService.findByEntityIdAndIdentifier(
              parkingSummaryDto.getEntityId(), parkingSummaryDto.getIdentifier());

      if (parking.isPresent()) {
        parking.get().setAvailable(parkingSummaryDto.getAvailable());
        parking.get().setLastUpdatedAt(parseDate(parkingSummaryDto.getLastUpdatedAt()));
        parking.get().setCurrentLicensePlate(parkingSummaryDto.getCurrentLicensePlate());
        parkingService.update(parking.get());
      } else {
        log.error("Parking not found");
      }
    } catch (Exception ex) {
      log.error("Unable to unpack message {}", ex.getMessage());
    }
  }
}
