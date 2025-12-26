package com.example.demo.repository;

import com.example.demo.model.PriceAdjustmentLog;
import java.util.List;

public interface PriceAdjustmentLogRepository {
    PriceAdjustmentLog save(PriceAdjustmentLog log);
    List<PriceAdjustmentLog> findByEventId(Long eventId);
}