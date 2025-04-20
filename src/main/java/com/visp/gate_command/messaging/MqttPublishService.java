package com.visp.gate_command.messaging;

public interface MqttPublishService {
  void publishMessage(String topic, Object payload);
}
