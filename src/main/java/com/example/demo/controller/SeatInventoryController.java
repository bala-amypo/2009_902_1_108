package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/inventory")
public class SeatInventoryController {
    
    private final SeatInventoryService inventoryService;
    
    public SeatInventoryController(SeatInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }
    
    @PostMapping
    public ApiResponse<SeatInventoryRecord> createInventory(@RequestBody SeatInventoryRecord inventory) {
        return ApiResponse.success(inventoryService.createInventory(inventory));
    }
    
    @GetMapping("/event/{eventId}")
    public ApiResponse<SeatInventoryRecord> getInventory(@PathVariable Long eventId) {
        return ApiResponse.success(inventoryService.getInventoryByEvent(eventId));
    }
}