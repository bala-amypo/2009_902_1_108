package com.example.demo.repository;

import com.example.demo.model.SeatInventoryRecord;
import java.util.Optional;

public interface SeatInventoryRecordRepository {
    SeatInventoryRecord save(SeatInventoryRecord inventory);
    Optional<SeatInventoryRecord> findByEventId(Long eventId);
}