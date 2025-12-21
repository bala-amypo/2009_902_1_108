package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
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

        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }

        SeatInventoryRecord inventory = inventoryRepository.findByEventId(eventId)
                .orElseThrow(() -> new BadRequestException("Seat inventory not found"));

        List<PricingRule> rules = ruleRepository.findByActiveTrue();

        long daysToEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
        double multiplier = rules.stream()
                .filter(r -> inventory.getRemainingSeats() >= r.getMinRemainingSeats()
                        && inventory.getRemainingSeats() <= r.getMaxRemainingSeats()
                        && daysToEvent <= r.getDaysBeforeEvent())
                .mapToDouble(PricingRule::getPriceMultiplier)
                .max()
                .orElse(1.0);

        double newPrice = event.getBasePrice() * multiplier;

        String appliedRules = rules.stream()
                .filter(r -> inventory.getRemainingSeats() >= r.getMinRemainingSeats()
                        && inventory.getRemainingSeats() <= r.getMaxRemainingSeats()
                        && daysToEvent <= r.getDaysBeforeEvent())
                .map(PricingRule::getRuleCode)
                .collect(Collectors.joining(","));

        DynamicPriceRecord priceRecord = new DynamicPriceRecord();
        priceRecord.setEvent(event); // ✅ Set Event entity
        priceRecord.setComputedPrice(newPrice);
        priceRecord.setAppliedRuleCodes(appliedRules);

        // Save price record
        priceRecord = priceRepository.save(priceRecord);

        // Log price adjustment if needed
        Optional<DynamicPriceRecord> lastPriceOpt = priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        double oldPrice = lastPriceOpt.map(DynamicPriceRecord::getComputedPrice).orElse(newPrice);

        if (Double.compare(newPrice, oldPrice) != 0) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEvent(event); // ✅ Set Event entity
            log.setOldPrice(oldPrice);
            log.setNewPrice(newPrice);
            log.setReason("Dynamic pricing adjustment");
            logRepository.save(log);
        }

        return priceRecord;
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public Optional<DynamicPriceRecord> getLatestPrice(Long eventId) {
        return priceRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepository.findAll();
    }
}
