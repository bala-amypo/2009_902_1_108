package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
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
    
    @PostMapping("/compute/{eventId}")
    public ApiResponse<DynamicPriceRecord> computePrice(@PathVariable Long eventId) {
        return ApiResponse.success(pricingService.computeDynamicPrice(eventId));
    }
    
    @GetMapping("/history/{eventId}")
    public ApiResponse<List<DynamicPriceRecord>> getPriceHistory(@PathVariable Long eventId) {
        return ApiResponse.success(pricingService.getPriceHistory(eventId));
    }
    
    @GetMapping("/all")
    public ApiResponse<List<DynamicPriceRecord>> getAllPrices() {
        return ApiResponse.success(pricingService.getAllComputedPrices());
    }
}