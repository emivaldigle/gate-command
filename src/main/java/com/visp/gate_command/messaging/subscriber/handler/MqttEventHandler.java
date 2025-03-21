package com.visp.gate_command.messaging.subscriber.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.domain.entity.Event;
import com.visp.gate_command.mapper.EventMapper;
import com.visp.gate_command.messaging.MqttSubscriberService;
import com.visp.gate_command.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttEventHandler implements MqttSubscriberService {
  private static final Logger log = LoggerFactory.getLogger(MqttEventHandler.class);
  private final EventRepository eventRepository;
  private final ObjectMapper objectMapper;
  private final EventMapper eventMapper;
  private final MqttClient client;

  @Override
  public void messageArrived(String topic, MqttMessage message) {
    try {
      EventDto eventDto = objectMapper.readValue(message.getPayload(), EventDto.class);
      Event event = eventMapper.toEntity(eventDto);
      // event.setCreatedAt(LocalDateTime.now());
      eventRepository.save(event);
      client.messageArrivedComplete(message.getId(), message.getQos());
    } catch (Exception ex) {
      log.info("problems unpacking message: {} on topic {}", topic, message);
    }
  }
}
