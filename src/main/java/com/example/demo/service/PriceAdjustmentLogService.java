package com.example.demo.service;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceAdjustmentLogService {

    @Autowired
    private PriceAdjustmentLogRepository repo;

    public PriceAdjustmentLog log(PriceAdjustmentLog log) {
        return repo.save(log);
    }
}
