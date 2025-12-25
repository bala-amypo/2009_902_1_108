package com.example.demo.controller;

import com.example.demo.model.PriceAdjustmentLog;
import com.example.demo.service.PriceAdjustmentLogService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
public class PriceAdjustmentLogController {

    private final PriceAdjustmentLogService logService;

    public PriceAdjustmentLogController(PriceAdjustmentLogService logService) {
        this.logService = logService;
    }

    @PostMapping("/log")
    public PriceAdjustmentLog logAdjustment(@RequestBody PriceAdjustmentLog log) {
        return logService.logAdjustment(log); // fixed
    }

    @GetMapping("/all")
    public List<PriceAdjustmentLog> getAllAdjustments() {
        return logService.getAllAdjustments(); // fixed
    }

    @GetMapping("/event/{eventId}")
    public List<PriceAdjustmentLog> getAdjustmentsByEvent(@PathVariable Long eventId) {
        return logService.getAdjustmentsByEvent(eventId);
    }
}
