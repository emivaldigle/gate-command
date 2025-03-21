package com.visp.gate_command.messaging.subscriber.handler;

import static com.visp.gate_command.util.DateUtils.parseDate;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.domain.dto.ParkingSummaryDto;
import com.visp.gate_command.domain.entity.Parking;
import com.visp.gate_command.messaging.MqttSubscriberService;
import com.visp.gate_command.repository.ParkingRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MqttParkingHandler implements MqttSubscriberService {
  private static final Logger log = LoggerFactory.getLogger(MqttParkingHandler.class);
  private final ParkingRepository parkingRepository;
  private final ObjectMapper objectMapper;
  private final MqttClient client;

  @Override
  @Transactional
  public void messageArrived(String topic, MqttMessage message) {
    try {
      ParkingSummaryDto parkingSummaryDto =
          objectMapper.readValue(message.getPayload(), ParkingSummaryDto.class);
      Optional<Parking> parking =
          parkingRepository.findByEntityIdAndIdentifier(
              parkingSummaryDto.getEntityId(), parkingSummaryDto.getIdentifier());

      if (parking.isPresent()) {
        parking.get().setAvailable(parkingSummaryDto.getAvailable());
        parking.get().setLastUpdatedAt(parseDate(parkingSummaryDto.getLastUpdatedAt()));
        parking.get().setCurrentLicensePlate(parkingSummaryDto.getCurrentLicensePlate());
        parkingRepository.updateParking(
            parkingSummaryDto.getCurrentLicensePlate(),
            parkingSummaryDto.getAvailable(),
            parseDate(parkingSummaryDto.getLastUpdatedAt()),
            parkingSummaryDto.getEntityId(),
            parkingSummaryDto.getIdentifier());
        client.messageArrivedComplete(message.getId(), message.getQos());
      } else {
        log.error("Parking not found");
      }
    } catch (Exception ex) {
      log.error("Unable to unpack message {}", ex.getMessage());
    }
  }
}
