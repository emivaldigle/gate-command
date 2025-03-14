package com.visp.gate_command.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@jakarta.persistence.Entity
@Table(name = "entities")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Entity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String type;

  @Column(nullable = false)
  private String address;

  @Column(nullable = false)
  private String taxId;

  @Column(nullable = false)
  private String contactPhone;

  @Column(nullable = false)
  private String region;

  @Column(nullable = false)
  private String city;

  @Column(nullable = false)
  private String commune;

  @Column(nullable = false)
  private int syncIntervalMinutes;

  @Column(nullable = false)
  private int parkingHoursAllowed;

  @Column(nullable = false)
  private int visitSizeLimit;

  @Column(nullable = false)
  private boolean active;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime lastUpdatedAt;
}
