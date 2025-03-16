package com.visp.gate_command.messaging;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public interface MqttSubscriberService {
  void messageArrived(String topic, MqttMessage message) throws Exception;
}
