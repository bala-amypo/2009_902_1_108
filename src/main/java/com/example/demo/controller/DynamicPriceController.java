package com.example.demo.controller;

import com.example.demo.service.DynamicPricingEngineService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dynamic-price")
public class DynamicPricingController {

    private final DynamicPricingEngineService pricingService;

    public DynamicPricingController(DynamicPricingEngineService pricingService) {
        this.pricingService = pricingService;
    }

    /**
     * Compute adjusted dynamic price for event
     *
     * POST /api/dynamic-price/compute/{eventId}
     */
    @PostMapping("/compute/{eventId}")
    public double computeDynamicPrice(@PathVariable Long eventId) {
        return pricingService.computeAdjustedPrice(eventId);
    }

    /**
     * Just test endpoint for health check
     */
    @GetMapping("/ping")
    public String ping() {
        return "Dynamic price engine running...";
    }
}
