package com.visp.gate_command.config;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MqttClientConfig {

  private static final String MQTT_BROKER = "tcp://172.18.0.2:1883";

  @Bean
  public MqttClient mqttClient() throws MqttException {
    MqttClient client = new MqttClient(MQTT_BROKER, MqttClient.generateClientId());
    MqttConnectOptions options = new MqttConnectOptions();
    options.setCleanSession(false);
    options.setAutomaticReconnect(true);
    client.connect(options);
    return client;
  }
}
