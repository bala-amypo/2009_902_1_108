package com.example.demo.repository;

import com.example.demo.model.EventRecord;
import java.util.List;
import java.util.Optional;

public interface EventRecordRepository {
    EventRecord save(EventRecord event);
    Optional<EventRecord> findById(Long id);
    Optional<EventRecord> findByEventCode(String eventCode);
    boolean existsByEventCode(String eventCode);
    List<EventRecord> findAll();
}