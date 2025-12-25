package com.example.demo.service.impl;

import com.example.demo.model.DynamicPriceRecord;
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
    public DynamicPriceRecord getLatestPrice(Long eventId) {
        SeatInventoryRecord seat = seatRepo.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));
        List<PricingRule> rules = ruleRepo.findByActiveTrue();

        double basePrice = seat.getBasePrice();
        double discount = 0;

        if (!rules.isEmpty()) {
            discount = rules.get(0).getDiscount();
        }

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setPrice(basePrice - discount);
        return record;
    }
}
