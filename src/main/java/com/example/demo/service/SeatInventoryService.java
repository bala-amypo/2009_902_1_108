package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;

import java.util.List;

public interface SeatInventoryService {
    SeatInventoryRecord createInventory(SeatInventoryRecord inv);
    SeatInventoryRecord getInventoryByEvent(Long eventId);
    SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats);  // MISSING
    List<SeatInventoryRecord> getAllInventories();                                  // MISSING
}
