package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {

    private final PriceAdjustmentLogRepository logRepo;

    public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository logRepo) {
        this.logRepo = logRepo;
    }

    @Override
    public PriceAdjustmentLog logAdjustment(PriceAdjustmentLog log) {
        return logRepo.save(log);
    }

    @Override
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
        return logRepo.findByEventId(eventId);
    }

    @Override
    public List<PriceAdjustmentLog> getAllAdjustments() {
        return logRepo.findAll();
    }

    @Override
    public PriceAdjustmentLog getLogById(Long id) {
        return logRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Adjustment log not found"));
    }
}
