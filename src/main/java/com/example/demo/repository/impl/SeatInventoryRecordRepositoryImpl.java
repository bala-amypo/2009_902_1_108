package com.example.demo.repository.impl;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.SeatInventoryRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class SeatInventoryRecordRepositoryImpl implements SeatInventoryRecordRepository {
    
    private final Map<Long, SeatInventoryRecord> inventory = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public SeatInventoryRecord save(SeatInventoryRecord record) {
        if (record.getId() == null) {
            record.setId(idCounter.getAndIncrement());
        }
        inventory.put(record.getId(), record);
        return record;
    }
    
    @Override
    public Optional<SeatInventoryRecord> findByEventId(Long eventId) {
        return inventory.values().stream()
                .filter(i -> i.getEventId().equals(eventId))
                .findFirst();
    }
}