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

    // POST /api/price-adjustments
    @PostMapping
    public PriceAdjustmentLog createLog(@RequestBody PriceAdjustmentLog log) {
        return logService.logAdjustment(log);
    }

    // GET /api/price-adjustments/event/{eventId}
    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getLogsByEvent(@PathVariable Long eventId) {
        return logService.getAdjustmentsByEvent(eventId);
    }

    // GET /api/price-adjustments
    @GetMapping
    public List<PriceAdjustmentLog> getAllLogs() {
        return logService.getAllAdjustments();
    }

    // GET /api/price-adjustments/{id}
    @GetMapping("/{id}")
    public PriceAdjustmentLog getLogById(@PathVariable Long id) {
        return logService.getLogById(id);
    }
}
