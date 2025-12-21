package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/price-adjustments")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogService logService;

    public PriceAdjustmentLogController(PriceAdjustmentLogService logService) {
        this.logService = logService;
    }

    // Log adjustment manually
    @PostMapping
    public PriceAdjustmentLog logAdjustment(@RequestBody PriceAdjustmentLog log) {
        return logService.logAdjustment(log);
    }

    // Get adjustments by event
    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(@PathVariable Long eventId) {
        return logService.getAdjustmentsByEvent(eventId);
    }

    // Get all adjustments
    @GetMapping
    public List<PriceAdjustmentLog> getAllAdjustments() {
        return logService.getAllAdjustments();
    }
}
