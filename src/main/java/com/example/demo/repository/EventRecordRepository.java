package com.example.demo.repository;

import com.example.demo.model.EventRecord;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class EventRecordRepository {
    
    private final Map<Long, EventRecord> events = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    public EventRecord save(EventRecord event) {
        if (event.getId() == null) {
            event.setId(idCounter.getAndIncrement());
        }
        events.put(event.getId(), event);
        return event;
    }
    
    public Optional<EventRecord> findById(Long id) {
        return Optional.ofNullable(events.get(id));
    }
    
    public Optional<EventRecord> findByEventCode(String eventCode) {
        return events.values().stream()
                .filter(e -> e.getEventCode().equals(eventCode))
                .findFirst();
    }
    
    public boolean existsByEventCode(String eventCode) {
        return events.values().stream()
                .anyMatch(e -> e.getEventCode().equals(eventCode));
    }
    
    public List<EventRecord> findAll() {
        return new ArrayList<>(events.values());
    }
}   