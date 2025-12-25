package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepository;
    private final SeatInventoryRecordRepository inventoryRepository;
    private final PricingRuleRepository ruleRepository;
    private final DynamicPriceRecordRepository priceRepository;
    private final PriceAdjustmentLogRepository logRepository;

    public DynamicPricingEngineServiceImpl(
            EventRecordRepository eventRepository,
            SeatInventoryRecordRepository inventoryRepository,
            PricingRuleRepository ruleRepository,
            DynamicPriceRecordRepository priceRepository,
            PriceAdjustmentLogRepository logRepository) {
        this.eventRepository = eventRepository;
        this.inventoryRepository = inventoryRepository;
        this.ruleRepository = ruleRepository;
        this.priceRepository = priceRepository;
        this.logRepository = logRepository;
    }

    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }

        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Seat inventory not found"));

        List<PricingRule> rules = ruleRepository.findByActiveTrue();
        double finalPrice = event.getBasePrice();
        List<String> appliedRules = new ArrayList<>();

        LocalDate now = LocalDate.now();
        long daysToEvent = now.until(event.getEventDate()).getDays();

        for (PricingRule rule : rules) {
            if (inventory.getRemainingSeats() >= rule.getMinRemainingSeats()
                    && inventory.getRemainingSeats() <= rule.getMaxRemainingSeats()
                    && daysToEvent <= rule.getDaysBeforeEvent()) {

                finalPrice = event.getBasePrice() * rule.getPriceMultiplier();
                appliedRules.add(rule.getRuleCode());
                break; // Apply first matching rule
            }
        }

        // Save dynamic price record
        DynamicPriceRecord previous = priceRepository
                .findFirstByEventIdOrderByComputedAtDesc(eventId)
                .orElse(null);

        DynamicPriceRecord current = new DynamicPriceRecord();
        current.setEventId(eventId);
        current.setComputedPrice(finalPrice);
        current.setAppliedRuleCodes(String.join(",", appliedRules));
        current.prePersist();

        priceRepository.save(current);

        // Log adjustment if price changed
        if (previous != null && !previous.getComputedPrice().equals(finalPrice)) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(previous.getComputedPrice());
            log.setNewPrice(finalPrice);
            log.prePersist();
            logRepository.save(log);
        }

        return current;
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll();
    }
}
