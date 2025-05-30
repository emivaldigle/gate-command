package com.visp.gate_command.repository;

import com.visp.gate_command.domain.entity.Entity;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntityRepository extends JpaRepository<Entity, UUID> {}
