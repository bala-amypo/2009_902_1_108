package com.example.demo.repository;

import com.example.demo.model.DynamicPriceRecord;

import java.util.List;
import java.util.Optional;

public interface DynamicPriceRecordRepository {

    DynamicPriceRecord save(DynamicPriceRecord record);

    Optional<DynamicPriceRecord> findFirstByEventIdOrderByComputedAtDesc(Long eventId);

    List<DynamicPriceRecord> findByEventIdOrderByComputedAtDesc(Long eventId);

    List<DynamicPriceRecord> findAll();
}
