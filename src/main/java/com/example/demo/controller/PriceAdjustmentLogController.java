package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/adjustments")
public class PriceAdjustmentLogController {
    
    private final PriceAdjustmentLogService adjustmentService;
    
    public PriceAdjustmentLogController(PriceAdjustmentLogService adjustmentService) {
        this.adjustmentService = adjustmentService;
    }
    
    @GetMapping("/event/{eventId}")
    public ApiResponse<List<PriceAdjustmentLog>> getAdjustments(@PathVariable Long eventId) {
        return ApiResponse.success(adjustmentService.getAdjustmentsByEvent(eventId));
    }
}