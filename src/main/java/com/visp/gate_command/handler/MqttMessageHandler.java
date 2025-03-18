package com.visp.gate_command.handler;

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
  private static final Logger log = LoggerFactory.getLogger(MqttMessageHandler.class);

  public void handleMessage(String topic, MqttMessage message) {
    String uuidPattern =
        "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    Pattern pattern = Pattern.compile(uuidPattern);
    Matcher matcher = pattern.matcher(topic);

    if (matcher.find()) {
      String uuid = matcher.group();
      String newTopic = topic.replace(uuid, "").replace("//", "/");
      switch (newTopic) {
        case "local-events/create":
          {
            eventHandler.messageArrived(topic, message);
            break;
          }
        case "local-parking/update":
          {
            parkingHandler.messageArrived(topic, message);
            break;
          }
        default:
          {
            log.info("Can't handle topic");
          }
      }
    } else {
      log.info("Topic bad constructed");
    }
  }
}
