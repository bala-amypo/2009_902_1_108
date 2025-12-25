package com.example.demo.repository;

import com.example.demo.model.SeatInventoryRecord;
import java.util.Optional;

public interface SeatInventoryRecordRepository {
    Optional<SeatInventoryRecord> findByEventCode(String eventCode);
    void save(SeatInventoryRecord record);
}
