package com.visp.gate_command.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.exception.UnableToPublishMessageException;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttPublishServiceImpl implements MqttPublishService {
  private static final Logger log = LoggerFactory.getLogger(MqttPublishServiceImpl.class);
  private final MqttClient mqttClient;
  private final ObjectMapper objectMapper;

  public void publishMessage(String topic, Object payload) {
    try {
      String jsonPayload = objectMapper.writeValueAsString(payload);
      MqttMessage message = new MqttMessage(jsonPayload.getBytes());
      message.setQos(2);
      mqttClient.publish(topic, message);
      log.info("Message published on topic {} with message: \n {}", topic, jsonPayload);
    } catch (JsonProcessingException | MqttException e) {
      throw new UnableToPublishMessageException(
          String.format("Unable to publish message %s", e.getMessage()));
    }
  }
}
