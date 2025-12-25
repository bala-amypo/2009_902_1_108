package com.example.demo.service.impl;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final SeatInventoryRecordRepository seatRepo;
    private final PricingRuleRepository ruleRepo;

    public DynamicPricingEngineServiceImpl(SeatInventoryRecordRepository seatRepo,
                                           PricingRuleRepository ruleRepo) {
        this.seatRepo = seatRepo;
        this.ruleRepo = ruleRepo;
    }

    @Override
    public Double getLatestPrice(Long eventId) {
        List<SeatInventoryRecord> seats = seatRepo.findByEventId(eventId);
        List<PricingRule> rules = ruleRepo.findByActiveTrue();
        // Simple example: calculate price using first active rule
        if(seats.isEmpty() || rules.isEmpty()) return 0.0;
        return seats.get(0).getBasePrice() * (1 - rules.get(0).getDiscount()/100.0);
    }
}
