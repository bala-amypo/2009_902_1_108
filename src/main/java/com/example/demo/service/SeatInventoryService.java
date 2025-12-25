package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;
import java.util.List;

public interface SeatInventoryService {
    SeatInventoryRecord getInventoryByEvent(Long eventId);
    SeatInventoryRecord createInventory(SeatInventoryRecord record);
    SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats);
    List<SeatInventoryRecord> getAllInventories();
}
