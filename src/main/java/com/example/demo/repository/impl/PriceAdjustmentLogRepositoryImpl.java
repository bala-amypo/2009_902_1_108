package com.example.demo.repository.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PriceAdjustmentLogRepositoryImpl implements PriceAdjustmentLogRepository {
    
    private final Map<Long, PriceAdjustmentLog> logs = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public PriceAdjustmentLog save(PriceAdjustmentLog log) {
        if (log.getId() == null) {
            log.setId(idCounter.getAndIncrement());
        }
        logs.put(log.getId(), log);
        return log;
    }
    
    @Override
    public List<PriceAdjustmentLog> findByEventId(Long eventId) {
        return logs.values().stream()
                .filter(l -> l.getEventId().equals(eventId))
                .collect(Collectors.toList());
    }
}