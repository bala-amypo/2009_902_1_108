package com.example.demo.repository.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRecordRepositoryImpl implements EventRecordRepository {
    
    private final Map<Long, EventRecord> events = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public EventRecord save(EventRecord event) {
        if (event.getId() == null) {
            event.setId(idCounter.getAndIncrement());
        }
        events.put(event.getId(), event);
        return event;
    }
    
    @Override
    public Optional<EventRecord> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }
    
    @Override
    public Optional<EventRecord> findByEventCode(String eventCode) {
        return events.values().stream()
                .filter(e -> e.getEventCode().equals(eventCode))
                .findFirst();
    }
    
    @Override
    public boolean existsByEventCode(String eventCode) {
        return events.values().stream()
                .anyMatch(e -> e.getEventCode().equals(eventCode));
    }
    
    @Override
    public List<EventRecord> findAll() {
        return new ArrayList<>(events.values());
    }
}