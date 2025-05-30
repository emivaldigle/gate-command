package com.visp.gate_command.domain.entity;

import com.visp.gate_command.domain.enums.EntityType;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
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
  @Id private UUID id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private EntityType type;

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
  private int parkingSizeLimit;

  @Column(nullable = false)
  private boolean active;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt;

  @Column(nullable = false)
  private LocalDateTime lastUpdatedAt;
}
