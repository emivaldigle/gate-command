package com.visp.gate_command.repository;

import com.visp.gate_command.domain.entity.Poc;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PocRepository extends JpaRepository<Poc, Long> {
  List<Poc> findByEntityId(Long entityId);
}
