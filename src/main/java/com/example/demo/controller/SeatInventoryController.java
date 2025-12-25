package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class SeatInventoryController {

    private final SeatInventoryService inventoryService;

    public SeatInventoryController(SeatInventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    @PostMapping("/create")
    public SeatInventoryRecord createInventory(@RequestBody SeatInventoryRecord inv) {
        return inventoryService.createInventory(inv);
    }

    @GetMapping("/event/{eventId}")
    public SeatInventoryRecord getInventory(@PathVariable Long eventId) {
        return inventoryService.getInventoryByEvent(eventId);
    }

    @PutMapping("/update/{eventId}")
    public SeatInventoryRecord updateRemainingSeats(@PathVariable Long eventId, @RequestParam Integer remainingSeats) {
        return inventoryService.updateRemainingSeats(eventId, remainingSeats); // fixed
    }

    @GetMapping("/all")
    public List<SeatInventoryRecord> getAllInventories() {
        return inventoryService.getAllInventories(); // fixed
    }
}
