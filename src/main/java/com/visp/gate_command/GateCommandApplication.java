package com.visp.gate_command;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaRepositories
@EnableAspectJAutoProxy
@EnableScheduling
@SpringBootApplication
public class GateCommandApplication {

  public static void main(String[] args) {
    SpringApplication.run(GateCommandApplication.class, args);
  }
}
