package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing")
public class DynamicPricingController {

    private final DynamicPricingEngineService pricingService;

    public DynamicPricingController(DynamicPricingEngineService pricingService) {
        this.pricingService = pricingService;
    }

    @GetMapping("/compute/{eventId}")
    public DynamicPriceRecord computePrice(@PathVariable Long eventId) {
        return pricingService.computeDynamicPrice(eventId);
    }

    @GetMapping("/latest/{eventId}")
    public DynamicPriceRecord getLatestPrice(@PathVariable Long eventId) {
        return pricingService.getLatestPrice(eventId); // fixed
    }

    @GetMapping("/history/{eventId}")
    public List<DynamicPriceRecord> getPriceHistory(@PathVariable Long eventId) {
        return pricingService.getPriceHistory(eventId);
    }

    @GetMapping("/all")
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return pricingService.getAllComputedPrices();
    }
}
