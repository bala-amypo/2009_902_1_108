package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepository;
    private final SeatInventoryRecordRepository inventoryRepository;
    private final PricingRuleRepository ruleRepository;
    private final DynamicPriceRecordRepository priceRepository;
    private final PriceAdjustmentLogRepository logRepository;

    public DynamicPricingEngineServiceImpl(EventRecordRepository eventRepository,
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
                .orElseThrow(() -> new BadRequestException("Event not found"));

        if (!Boolean.TRUE.equals(event.getActive())) {
            throw new BadRequestException("Event is not active");
        }

        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId).stream().findFirst()
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        List<PricingRule> rules = ruleRepository.findAll().stream()
                .filter(r -> Boolean.TRUE.equals(r.getActive()))
                .collect(Collectors.toList());

        // Calculate days to event
        long daysToEvent = Duration.between(LocalDate.now().atStartOfDay(), event.getEventDate().atStartOfDay()).toDays();

        // Find highest matching multiplier
        double multiplier = rules.stream()
                .filter(r -> (inventory.getRemainingSeats() >= r.getMinRemainingSeats() &&
                        inventory.getRemainingSeats() <= r.getMaxRemainingSeats()) &&
                        daysToEvent <= r.getDaysBeforeEvent())
                .map(PricingRule::getPriceMultiplier)
                .max(Double::compare)
                .orElse(1.0);

        double computedPrice = event.getBasePrice() * multiplier;

        // Save DynamicPriceRecord
        DynamicPriceRecord priceRecord = new DynamicPriceRecord();
        priceRecord.setEventId(eventId);
        priceRecord.setComputedPrice(computedPrice);
        priceRecord.setAppliedRuleCodes(
                rules.stream()
                        .filter(r -> (inventory.getRemainingSeats() >= r.getMinRemainingSeats() &&
                                inventory.getRemainingSeats() <= r.getMaxRemainingSeats()) &&
                                daysToEvent <= r.getDaysBeforeEvent())
                        .map(PricingRule::getRuleCode)
                        .collect(Collectors.joining(","))
        );

        priceRecord = priceRepository.save(priceRecord);

        // Log price adjustment if previous price exists
        priceRepository.findByEventId(eventId).stream()
                .max(Comparator.comparing(DynamicPriceRecord::getComputedAt))
                .ifPresent(prev -> {
                    if (Math.abs(prev.getComputedPrice() - computedPrice) > 0.01) {
                        PriceAdjustmentLog log = new PriceAdjustmentLog();
                        log.setEventId(eventId);
                        log.setOldPrice(prev.getComputedPrice());
                        log.setNewPrice(computedPrice);
                        log.setReason("Dynamic pricing adjustment");
                        logRepository.save(log);
                    }
                });

        return priceRecord;
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepository.findByEventId(eventId)
                .stream()
                .sorted((a, b) -> b.getComputedAt().compareTo(a.getComputedAt()))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        return priceRepository.findByEventId(eventId)
                .stream()
                .max(Comparator.comparing(DynamicPriceRecord::getComputedAt));
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll();
    }
}
