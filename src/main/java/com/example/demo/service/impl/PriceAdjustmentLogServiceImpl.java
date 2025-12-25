package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {

    private final PriceAdjustmentLogRepository repo;

    public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository repo) {
        this.repo = repo;
    }

    @Override
    public PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log) {
        return repo.save(log);
    }

    @Override
    public List<PriceAdjustmentLog> getAllAdjustments() {
        return repo.findAll();
    }

    @Override
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
        return repo.findByEventId(eventId);
    }
}
