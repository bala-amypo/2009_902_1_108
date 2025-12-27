package com.example.demo.repository;

import com.example.demo.model.DynamicPriceRecord;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class DynamicPriceRecordRepository {
    
    private final Map<Long, DynamicPriceRecord> records = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    public DynamicPriceRecord save(DynamicPriceRecord record) {
        if (record.getId() == null) {
            record.setId(idCounter.getAndIncrement());
        }
        records.put(record.getId(), record);
        return record;
    }
    
    public List<DynamicPriceRecord> findAll() {
        return new ArrayList<>(records.values());
    }
    
    public List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId) {
        return records.values().stream()
                .filter(r -> r.getEventId().equals(eventId))
                .sorted((a, b) -> b.getComputedAt().compareTo(a.getComputedAt()))
                .collect(Collectors.toList());
    }
    
    public Optional<DynamicPriceRecord> findFirstByEventIdOrderByComputedAtDesc(Long eventId) {
        return records.values().stream()
                .filter(r -> r.getEventId().equals(eventId))
                .max(Comparator.comparing(DynamicPriceRecord::getComputedAt));
    }
}