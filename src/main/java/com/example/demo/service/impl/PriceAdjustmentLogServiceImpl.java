package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.service.PriceAdjustmentLogService;

import java.util.List;

public class PriceAdjustmentLogServiceImpl implements PriceAdjustmentLogService {
    
    private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;
    
    public PriceAdjustmentLogServiceImpl(PriceAdjustmentLogRepository priceAdjustmentLogRepository) {
        this.priceAdjustmentLogRepository = priceAdjustmentLogRepository;
    }
    
    @Override
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(Long eventId) {
        return priceAdjustmentLogRepository.findByEventId(eventId);
    }
}