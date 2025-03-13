package com.visp.gate_command.domain.dto;

import lombok.Data;

@Data
public class EntityDto {

  private Long id;

  private String name;

  private String type;

  private String address;

  private String region;

  private String city;

  private String commune;

  private String taxId;

  private String contactPhone;

  private int syncIntervalMinutes;

  private int parkingHoursAllowed;
}
