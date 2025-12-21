package com.example.demo.controller;

import com.example.demo.model.DynamicPriceRecord;
import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dynamic-pricing")
public class DynamicPricingController {

    private final DynamicPricingEngineService pricingService;

    public DynamicPricingController(DynamicPricingEngineService pricingService) {
        this.pricingService = pricingService;
    }

    // Compute dynamic price
    @PostMapping("/compute/{eventId}")
    public DynamicPriceRecord computeDynamicPrice(@PathVariable Long eventId) {
        return pricingService.computeDynamicPrice(eventId);
    }

    // Get latest price
    @GetMapping("/latest/{eventId}")
    public DynamicPriceRecord getLatestPrice(@PathVariable Long eventId) {
        return pricingService.getLatestPrice(eventId)
                .orElseThrow(() -> new RuntimeException("No price found for event " + eventId));
    }

    // Get price history
    @GetMapping("/history/{eventId}")
    public List<DynamicPriceRecord> getPriceHistory(@PathVariable Long eventId) {
        return pricingService.getPriceHistory(eventId);
    }

    // Get all computed prices
    @GetMapping
    public List<DynamicPriceRecord> getAllComputedPrices() {
        return pricingService.getAllComputedPrices();
    }
}
