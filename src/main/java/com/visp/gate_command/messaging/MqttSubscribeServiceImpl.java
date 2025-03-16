package com.visp.gate_command.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.visp.gate_command.aop.Loggable;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.domain.entity.Event;
import com.visp.gate_command.mapper.EventMapper;
import com.visp.gate_command.repository.EventRepository;
import java.time.LocalDateTime;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Loggable
public class MqttSubscribeServiceImpl implements MqttSubscriberService {

  private final EventRepository eventRepository;
  private final ObjectMapper objectMapper;
  private final EventMapper eventMapper;

  private final String MQTT_BROKER = "tcp://172.18.0.2:1883";
  private final String TOPIC = "gate/events";

  private MqttClient mqttClient;

  @Autowired
  public MqttSubscribeServiceImpl(
      EventRepository eventRepository, ObjectMapper objectMapper, EventMapper eventMapper)
      throws MqttException {
    this.eventRepository = eventRepository;
    this.objectMapper = objectMapper;
    this.eventMapper = eventMapper;
    mqttClient = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
    mqttClient.connect();
    mqttClient.subscribe(TOPIC, this::messageArrived);
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    System.out.println(message);
    EventDto eventDto = objectMapper.readValue(message.getPayload(), EventDto.class);
    Event event = eventMapper.toEntity(eventDto);
    event.setCreatedAt(LocalDateTime.now());
    eventRepository.save(event);
  }
}
