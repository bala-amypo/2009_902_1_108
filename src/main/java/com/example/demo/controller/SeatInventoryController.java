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

    // Create inventory
    @PostMapping
    public SeatInventoryRecord createInventory(@RequestBody SeatInventoryRecord inventory) {
        return inventoryService.createInventory(inventory);
    }

    // Update remaining seats
    @PutMapping("/{eventId}/remaining")
    public SeatInventoryRecord updateRemainingSeats(@PathVariable Long eventId,
                                                    @RequestParam Integer remainingSeats) {
        return inventoryService.updateRemainingSeats(eventId, remainingSeats);
    }

    // Get inventory by event
    @GetMapping("/event/{eventId}")
    public SeatInventoryRecord getInventoryByEvent(@PathVariable Long eventId) {
        return inventoryService.getInventoryByEvent(eventId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for event " + eventId));
    }

    // Get all inventories
    @GetMapping
    public List<SeatInventoryRecord> getAllInventories() {
        return inventoryService.getAllInventories();
    }
}
