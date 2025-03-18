package com.visp.gate_command.repository;

import com.visp.gate_command.domain.entity.Event;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends JpaRepository<Event, UUID> {
  List<Event> findByPocEntityIdAndCreatedAtBetween(
      UUID pocEntityId, LocalDateTime startOfDay, LocalDateTime endOfDay);

  Optional<Event> findFirstByPocEntityIdAndPlate(UUID entityId, String plate);
}
