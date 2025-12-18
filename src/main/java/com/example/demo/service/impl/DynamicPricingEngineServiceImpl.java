package com.example.demo.service.impl;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.model.SaleEvent;
import com.example.demo.repository.PriceAdjustmentLogRepository;
import com.example.demo.repository.SaleEventRepository;
import com.example.demo.service.DynamicPricingEngineService;
import com.example.demo.service.pricing.PricingStrategy;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Service
public class DynamicPricingEngineServiceImpl implements DynamicPricingEngineService {

    private final SaleEventRepository eventRepo;
    private final PriceAdjustmentLogRepository logRepo;
    private final PricingStrategy defaultStrategy;
    private final PricingStrategy lowStockStrategy;
    private final PricingStrategy highDemandStrategy;

    public DynamicPricingEngineServiceImpl(
            SaleEventRepository eventRepo,
            PriceAdjustmentLogRepository logRepo,
            PricingStrategy defaultStrategy,
            PricingStrategy lowStockStrategy,
            PricingStrategy highDemandStrategy
    ) {
        this.eventRepo = eventRepo;
        this.logRepo = logRepo;
        this.defaultStrategy = defaultStrategy;
        this.lowStockStrategy = lowStockStrategy;
        this.highDemandStrategy = highDemandStrategy;
    }

    @Override
    public double computeAdjustedPrice(Long eventId) {

        SaleEvent event = eventRepo.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found"));

        double base = event.getBasePrice();
        double demandFactor = event.getDemandFactor();
        double season = event.getSeasonMultiplier();

        PricingStrategy strategy = determineStrategy(event);

        double price = strategy.apply(base, demandFactor, season);

        BigDecimal finalPrice = BigDecimal.valueOf(price)
                .setScale(2, RoundingMode.HALF_UP);

        PriceAdjustmentLog log = new PriceAdjustmentLog();
        log.setEvent(event);
        log.setNewPrice(finalPrice.doubleValue());
        logRepo.save(log);

        return finalPrice.doubleValue();
    }

    private PricingStrategy determineStrategy(SaleEvent event) {

        if (event.getRemainingStock() < 10) {
            return lowStockStrategy;
        }
        if (event.getDemandFactor() > 1.5) {
            return highDemandStrategy;
        }
        return defaultStrategy;
    }
}
