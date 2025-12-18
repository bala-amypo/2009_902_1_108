package com.example.demo.service;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.DynamicPriceRecordRepository;
import com.example.demo.repository.PricingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DynamicPricingEngineService {

    @Autowired
    private PricingRuleRepository ruleRepo;

    @Autowired
    private DynamicPriceRecordRepository priceRecordRepo;

    public double calculatePrice(Long eventId, int seatsSold, int totalSeats, double basePrice) {

        double finalPrice = basePrice;

        List<PricingRule> rules = ruleRepo.findByEventId(eventId);

        for (PricingRule rule : rules) {

            if (seatsSold == rule.getTriggerThreshold()) {

                switch (rule.getOperator()) {
                    case GREATER_THAN:
                        if (seatsSold > rule.getTriggerThreshold())
                            finalPrice += rule.getPriceChangeAmount();
                        break;

                    case LESS_THAN:
                        if (seatsSold < rule.getTriggerThreshold())
                            finalPrice -= rule.getPriceChangeAmount();
                        break;

                    case EQUAL:
                        if (seatsSold == rule.getTriggerThreshold())
                            finalPrice += rule.getPriceChangeAmount();
                        break;

                    case PERCENT:
                        finalPrice += (finalPrice * rule.getPriceChangeAmount()) / 100.0;
                        break;

                }
            }
        }

        DynamicPriceRecord record = new DynamicPriceRecord();
        record.setEventId(eventId);
        record.setBasePrice(basePrice);
        record.setAdjustedPrice(finalPrice);
        record.setDemandFactor((seatsSold * 100.0) / totalSeats);
        record.setTimestamp(LocalDateTime.now());

        priceRecordRepo.save(record);

        return finalPrice;
    }
}
