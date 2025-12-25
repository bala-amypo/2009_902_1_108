package com.example.demo.service.impl;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.DynamicPriceRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final SeatInventoryRecordRepository seatRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository priceRepo;

    public DynamicPricingEngineServiceImpl(SeatInventoryRecordRepository seatRepo,
                                           PricingRuleRepository ruleRepo,
                                           DynamicPriceRecordRepository priceRepo) {
        this.seatRepo = seatRepo;
        this.ruleRepo = ruleRepo;
        this.priceRepo = priceRepo;
    }

    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {
        SeatInventoryRecord seat = seatRepo.findByEventId(eventId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));

        List<PricingRule> rules = ruleRepo.findByActiveTrue();
        double discount = rules.isEmpty() ? 0 : rules.get(0).getDiscount();

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEvent(seat.getEvent());
        record.setComputedPrice(seat.getBasePrice() - discount);
        record.setAppliedRuleCodes(rules.isEmpty() ? "" : rules.get(0).getRuleCode());

        return priceRepo.save(record);
    }

    @Override
    public List<DynamicPriceRecord> getPriceHistory(Long eventId) {
        return priceRepo.findByEventIdOrderByComputedAtDesc(eventId);
    }

    @Override
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return priceRepo.findAll();
    }
}
