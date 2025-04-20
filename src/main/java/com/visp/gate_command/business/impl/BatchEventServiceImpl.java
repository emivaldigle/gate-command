package com.visp.gate_command.business.impl;

import com.visp.gate_command.business.BatchEventService;
import com.visp.gate_command.domain.dto.EventBatchDto;
import com.visp.gate_command.domain.dto.EventDto;
import com.visp.gate_command.mapper.EventMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BatchEventServiceImpl implements BatchEventService {
  @PersistenceContext private EntityManager entityManager;
  private final EventMapper eventMapper;
  @Transactional
  @Override
  public void saveBatch(EventBatchDto eventBatchDto) {
    int batchSize = 50; // this must match the same property inside hibernate
    int i = 0;

    for (EventDto eventDto : eventBatchDto.getEvents()) {
      final var event = eventMapper.toEntity(eventDto);
      entityManager.persist(event);

      if (i % batchSize == 0 && i > 0) {
        entityManager.flush();
        entityManager.clear();
      }
      i++;
    }
    entityManager.flush();
    entityManager.clear();
  }
}
