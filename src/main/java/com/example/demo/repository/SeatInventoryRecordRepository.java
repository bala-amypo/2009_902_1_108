package com.example.demo.repository;

import com.example.demo.model.SeatInventoryRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface SeatInventoryRecordRepository extends JpaRepository<SeatInventoryRecord, Long> {
    List<SeatInventoryRecord> findByEventId(Long eventId);
    Optional<SeatInventoryRecord> findFirstByEventId(Long eventId);
}
