package com.example.demo.repository;

import com.example.demo.model.EventRecord;
import java.util.Optional;

public interface EventRecordRepository {
    boolean existsByEventCode(String eventCode);
    Optional<EventRecord> findByEventCode(String eventCode);
    void save(EventRecord record);
}
