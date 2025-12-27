package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.*;
import com.example.demo.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class DynamicPricingEngineService {
    
    private final EventRecordRepository eventRecordRepository;
    private final SeatInventoryRecordRepository seatInventoryRecordRepository;
    private final PricingRuleRepository pricingRuleRepository;
    private final DynamicPriceRecordRepository dynamicPriceRecordRepository;
    private final PriceAdjustmentLogRepository priceAdjustmentLogRepository;
    
    public DynamicPricingEngineService(EventRecordRepository eventRecordRepository,
                                      SeatInventoryRecordRepository seatInventoryRecordRepository,
                                      PricingRuleRepository pricingRuleRepository,
                                      DynamicPriceRecordRepository dynamicPriceRecordRepository,
                                      PriceAdjustmentLogRepository priceAdjustmentLogRepository) {
        this.eventRecordRepository = eventRecordRepository;
        this.seatInventoryRecordRepository = seatInventoryRecordRepository;
        this.pricingRuleRepository = pricingRuleRepository;
        this.dynamicPriceRecordRepository = dynamicPriceRecordRepository;
        this.priceAdjustmentLogRepository = priceAdjustmentLogRepository;
    }
    
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        EventRecord event = eventRecordRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));
        
        if (!event.getActive()) {
            throw new BadRequestException("Event is not active");
        }
        
        SeatInventoryRecord inventory = seatInventoryRecordRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Seat inventory not found"));
        
        List<PricingRule> activeRules = pricingRuleRepository.findByActiveTrue();
        
        double basePrice = event.getBasePrice();
        double computedPrice = basePrice;
        String appliedRules = "";
        
        if (!activeRules.isEmpty()) {
            long daysUntilEvent = ChronoUnit.DAYS.between(LocalDate.now(), event.getEventDate());
            
            for (PricingRule rule : activeRules) {
                if (inventory.getRemainingSeats() >= rule.getMinRemainingSeats() &&
                    inventory.getRemainingSeats() <= rule.getMaxRemainingSeats() &&
                    daysUntilEvent <= rule.getDaysBeforeEvent()) {
                    computedPrice = basePrice * rule.getPriceMultiplier();
                    appliedRules = rule.getRuleCode();
                    break;
                }
            }
        }
        
        // Check for price change and log adjustment
        Optional<DynamicPriceRecord> previousPrice = 
                dynamicPriceRecordRepository.findFirstByEventIdOrderByComputedAtDesc(eventId);
        
        if (previousPrice.isPresent() && !previousPrice.get().getComputedPrice().equals(computedPrice)) {
            PriceAdjustmentLog log = new PriceAdjustmentLog();
            log.setEventId(eventId);
            log.setOldPrice(previousPrice.get().getComputedPrice());
            log.setNewPrice(computedPrice);
            log.prePersist();
            priceAdjustmentLogRepository.save(log);
        }
        
        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setComputedPrice(computedPrice);
        record.setAppliedRuleCodes(appliedRules);
        record.prePersist();
        
        return dynamicPriceRecordRepository.save(record);
    }
    
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return dynamicPriceRecordRepository.findByEventIdOrderByComputedAtDesc(eventId);
    }
    
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return dynamicPriceRecordRepository.findAll();
    }
}