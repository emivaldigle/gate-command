package com.visp.gate_command.messaging.subscriber.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.business.BatchEventService;
import com.visp.gate_command.domain.dto.EventBatchDto;
import com.visp.gate_command.messaging.MqttSubscriberService;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttPendingEventHandler implements MqttSubscriberService {
  private static final Logger log = LoggerFactory.getLogger(MqttPendingEventHandler.class);
  private final BatchEventService batchEventService;
  private final ObjectMapper objectMapper;
  private final MqttClient client;

  @Override
  public void messageArrived(String topic, MqttMessage message) {
    try {

      EventBatchDto events = objectMapper.readValue(message.getPayload(), EventBatchDto.class);
      batchEventService.saveBatch(events);
      client.messageArrivedComplete(message.getId(), message.getQos());
    } catch (Exception ex) {
      log.info("problems unpacking message: {} on topic {}", topic, message);
    }
  }
}
