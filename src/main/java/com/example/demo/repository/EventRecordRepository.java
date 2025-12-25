package com.example.demo.repository;

import com.example.demo.model.EventRecord;

import java.util.List;
import java.util.Optional;

public interface EventRecordRepository {

    boolean existsByEventCode(String eventCode);

    EventRecord save(EventRecord event);

    Optional<EventRecord> findById(Long id);

    Optional<EventRecord> findByEventCode(String eventCode);

    List<EventRecord> findAll();
}
