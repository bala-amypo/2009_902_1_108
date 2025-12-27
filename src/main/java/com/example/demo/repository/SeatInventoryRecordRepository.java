package com.example.demo.repository;

import com.example.demo.model.SeatInventoryRecord;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SeatInventoryRecordRepository {
    
    private final Map<Long, SeatInventoryRecord> inventory = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    public SeatInventoryRecord save(SeatInventoryRecord record) {
        if (record.getId() == null) {
            record.setId(idCounter.getAndIncrement());
        }
        inventory.put(record.getId(), record);
        return record;
    }
    
    public Optional<SeatInventoryRecord> findByEventId(Long eventId) {
        return inventory.values().stream()
                .filter(i -> i.getEventId().equals(eventId))
                .findFirst();
    }
}