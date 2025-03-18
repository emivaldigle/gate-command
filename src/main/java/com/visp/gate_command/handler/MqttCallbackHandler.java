package com.visp.gate_command.handler;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MqttCallbackHandler implements MqttCallback {

  private static final Logger log = LoggerFactory.getLogger(MqttCallbackHandler.class);

  @Autowired private MqttMessageHandler messageHandler;

  @Override
  public void connectionLost(Throwable cause) {
    log.error("Connection lost with MQTT broker", cause);
  }

  @Override
  public void messageArrived(String topic, MqttMessage message) throws Exception {
    messageHandler.handleMessage(topic, message);
  }

  @Override
  public void deliveryComplete(IMqttDeliveryToken token) {
    log.info("Delivery completed");
  }
}
