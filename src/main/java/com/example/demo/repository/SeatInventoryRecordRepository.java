package com.example.demo.repository;

import com.example.demo.model.SeatInventoryRecord;
import java.util.Optional;

public interface SeatInventoryRecordRepository {
    SeatInventoryRecord save(SeatInventoryRecord inv);
    Optional<SeatInventoryRecord> findByEventId(Long eventId);
}
