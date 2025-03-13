package com.visp.gate_command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class GateCommandApplication {

  public static void main(String[] args) {
    SpringApplication.run(GateCommandApplication.class, args);
  }
}
