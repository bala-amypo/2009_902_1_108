package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final SeatInventoryRecordRepository seatRepo;
    private final PricingRuleRepository ruleRepo;

    public DynamicPricingEngineServiceImpl(SeatInventoryRecordRepository seatRepo, PricingRuleRepository ruleRepo) {
        this.seatRepo = seatRepo;
        this.ruleRepo = ruleRepo;
    }

    @Override
    public double getLatestPrice(Long eventId) {
        // Example logic: get available seats for the event
        List<SeatInventoryRecord> seats = seatRepo.findByEventId(eventId);
        List<PricingRule> activeRules = ruleRepo.findByActiveTrue().stream().toList();

        // Dummy pricing logic for demo
        double basePrice = 100.0;
        if (!activeRules.isEmpty()) {
            basePrice *= 1.2; // apply rule adjustment
        }
        return basePrice;
    }
}
