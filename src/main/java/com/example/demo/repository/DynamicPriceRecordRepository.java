package com.example.demo.repository;

import com.example.demo.model.DynamicPriceRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface DynamicPriceRecordRepository extends JpaRepository<DynamicPriceRecord, Long> {

    // Get the latest record for a specific event
    Optional<DynamicPriceRecord> findFirstByEventIdOrderByComputedAtDesc(Long eventId);

    // Get all records for a specific event, sorted by computedAt descending
    List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId);
}
