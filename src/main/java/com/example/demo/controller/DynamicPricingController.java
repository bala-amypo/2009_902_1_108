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

    @GetMapping("/{eventId}")
    public DynamicPriceRecord getPrice(@PathVariable Long eventId) {
        // Call the existing computeDynamicPrice() method
        return pricingService.computeDynamicPrice(eventId);
    }

    @GetMapping("/{eventId}/history")
    public List<DynamicPriceRecord> getPriceHistory(@PathVariable Long eventId) {
        return pricingService.getPriceHistory(eventId);
    }

    @GetMapping("/all")
    public List<DynamicPriceRecord> getAllPrices() {
        return pricingService.getAllComputedPrices();
    }
}
