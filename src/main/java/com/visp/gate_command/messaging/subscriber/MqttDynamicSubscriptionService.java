package com.visp.gate_command.messaging.subscriber;

import com.visp.gate_command.domain.entity.Entity;
import com.visp.gate_command.messaging.subscriber.handler.MqttCallbackHandler;
import com.visp.gate_command.repository.EntityRepository;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttDynamicSubscriptionService {
  private static final Logger log = LoggerFactory.getLogger(MqttDynamicSubscriptionService.class);

  private final MqttClient mqttClient;
  private final EntityRepository entityRepository;
  private final MqttCallbackHandler mqttCallbackHandler;

  private static final String TOPIC_LOCAL_EVENTS = "local-events/%s/create";
  private static final String TOPIC_LOCAL_PARKING = "local-parking/%s/update";
  private static final String TOPIC_PENDING_EVENTS = "pending-events/%s/sync";
  private final Set<String> subscribedTopics = new HashSet<>();

  @Scheduled(fixedRate = 60000)
  public void updateSubscriptions() {
    try {
      mqttClient.setCallback(mqttCallbackHandler);

      List<Entity> activeSubscriptions =
          entityRepository.findAll().stream().filter(Entity::isActive).toList();

      Set<String> currentTopics =
          activeSubscriptions.stream()
              .map(Entity::getId)
              .flatMap(
                  id ->
                      Set.of(
                          String.format(TOPIC_LOCAL_EVENTS, id),
                          String.format(TOPIC_LOCAL_PARKING, id),
                          String.format(TOPIC_PENDING_EVENTS, id))
                          .stream())
              .collect(Collectors.toSet());

      subscribedTopics.stream()
          .filter(topic -> !currentTopics.contains(topic))
          .forEach(this::unsubscribe);

      currentTopics.stream()
          .filter(topic -> !subscribedTopics.contains(topic))
          .forEach(this::subscribe);

      subscribedTopics.clear();
      subscribedTopics.addAll(currentTopics);

      log.info("Subscriptions updated. Current topics: {}", subscribedTopics);
    } catch (Exception e) {
      log.error("Error updating MQTT subscriptions", e);
    }
  }

  private void subscribe(String topic) {
    try {
      mqttClient.subscribe(topic);
      log.info("Subscribed to topic: {}", topic);
    } catch (MqttException e) {
      log.error("Failed to subscribe to topic: {}", topic, e);
    }
  }

  private void unsubscribe(String topic) {
    try {
      mqttClient.unsubscribe(topic);
      log.info("Unsubscribed from topic: {}", topic);
    } catch (MqttException e) {
      log.error("Failed to unsubscribe from topic: {}", topic, e);
    }
  }
}
