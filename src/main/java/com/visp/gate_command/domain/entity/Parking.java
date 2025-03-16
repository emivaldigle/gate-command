package com.visp.gate_command.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(
    name = "parking",
    uniqueConstraints = {@UniqueConstraint(columnNames = "identifier")})
@AllArgsConstructor
@NoArgsConstructor
public class Parking {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "entity_id", nullable = false)
  private com.visp.gate_command.domain.entity.Entity entity;

  @Column(nullable = false)
  private String identifier;

  @Column private String currentLicensePlate;

  @Column(nullable = false)
  private Boolean isForVisit;

  @Column(nullable = false)
  private Boolean available;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  @Column private LocalDateTime expirationDate;
}
