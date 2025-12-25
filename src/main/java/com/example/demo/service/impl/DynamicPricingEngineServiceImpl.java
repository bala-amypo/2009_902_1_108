package com.example.demo.service.impl;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.model.EventRecord;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.DynamicPriceRecordRepository;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final EventRecordRepository eventRepo;
    private final PricingRuleRepository ruleRepo;
    private final DynamicPriceRecordRepository priceRepo;

    public DynamicPricingEngineServiceImpl(EventRecordRepository eventRepo,
                                           PricingRuleRepository ruleRepo,
                                           DynamicPriceRecordRepository priceRepo) {
        this.eventRepo = eventRepo;
        this.ruleRepo = ruleRepo;
        this.priceRepo = priceRepo;
    }

    @Override
    public DynamicPriceRecord computeDynamicPrice(Long eventId) {

        EventRecord event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found: " + eventId));

        List<PricingRule> rules = ruleRepo.findByActiveTrue();

        double multiplier = rules.isEmpty()
                ? 1.0
                : rules.get(0).getPriceMultiplier();

        double finalPrice = event.getBasePrice() * multiplier;

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEvent(event);
        record.setComputedPrice(finalPrice);
        record.setAppliedRuleCodes(
                rules.isEmpty() ? "NONE" : rules.get(0).getRuleCode()
        );

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
