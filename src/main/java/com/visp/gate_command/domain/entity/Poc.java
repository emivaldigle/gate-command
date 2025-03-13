package com.visp.gate_command.domain.entity;

import com.visp.gate_command.domain.enums.PocType;
import jakarta.persistence.Column;
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
@jakarta.persistence.Entity
@Table(
    name = "pocs",
    uniqueConstraints = {@UniqueConstraint(columnNames = "identifier")})
@AllArgsConstructor
@NoArgsConstructor
public class Poc {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String identifier;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private PocType type;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "residency_id", nullable = false)
  private Entity entity;

  private String location;

  private LocalDateTime lastSync;

  @Column(nullable = false, updatable = false)
  private LocalDateTime createdAt = LocalDateTime.now();

  ;
}
