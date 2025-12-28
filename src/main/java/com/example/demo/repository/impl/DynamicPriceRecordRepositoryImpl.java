package com.example.demo.repository.impl;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.repository.DynamicPriceRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class DynamicPriceRecordRepositoryImpl implements DynamicPriceRecordRepository {
    
    private final Map<Long, DynamicPriceRecord> records = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public DynamicPriceRecord save(DynamicPriceRecord record) {
        if (record.getId() == null) {
            record.setId(idCounter.getAndIncrement());
        }
        records.put(record.getId(), record);
        return record;
    }
    
    @Override
    public List<DynamicPriceRecord> findAll() {
        return new ArrayList<>(records.values());
    }
    
    @Override
    public List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId) {
        return records.values().stream()
                .filter(r -> r.getEventId().equals(eventId))
                .sorted((a, b) -> b.getComputedAt().compareTo(a.getComputedAt()))
                .collect(Collectors.toList());
    }
    
    @Override
    public Optional<DynamicPriceRecord> findFirstByEventIdOrderByComputedAtDesc(Long eventId) {
        return records.values().stream()
                .filter(r -> r.getEventId().equals(eventId))
                .max(Comparator.comparing(DynamicPriceRecord::getComputedAt));
    }
}