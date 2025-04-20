package com.visp.gate_command.messaging.subscriber.handler;

import jakarta.annotation.PostConstruct;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MqttMessageHandler {
  private final MqttEventHandler eventHandler;
  private final MqttParkingHandler parkingHandler;
  private final MqttPendingEventHandler pendingEventHandler;
  private static final Logger log = LoggerFactory.getLogger(MqttMessageHandler.class);
  private Map<String, BiConsumer<String, MqttMessage>> topicHandlers;

  @PostConstruct
  public void init() {
    this.topicHandlers =
        Map.of(
            "local-events/create", eventHandler::messageArrived,
            "local-parking/update", parkingHandler::messageArrived,
            "pending-events/sync", pendingEventHandler::messageArrived);
  }

  public void handleMessage(String topic, MqttMessage message) {
    String uuidPattern =
        "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";
    log.info("Message arrived on topic {} with message: \n {}", topic, message);

    String newTopic =
        extractTopic(topic, uuidPattern)
            .orElseGet(
                () -> {
                  log.info("Topic bad constructed");
                  return null;
                });

    if (newTopic != null) {
      topicHandlers
          .getOrDefault(newTopic, (t, m) -> log.info("Can't handle topic"))
          .accept(topic, message);
    }
  }

  private Optional<String> extractTopic(String topic, String uuidPattern) {
    Pattern pattern = Pattern.compile(uuidPattern);
    Matcher matcher = pattern.matcher(topic);
    return matcher.find()
        ? Optional.of(topic.replace(matcher.group(), "").replace("//", "/"))
        : Optional.empty();
  }
}
