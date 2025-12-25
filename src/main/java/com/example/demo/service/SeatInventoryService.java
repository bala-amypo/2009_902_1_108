package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;

public interface SeatInventoryService {
    SeatInventoryRecord createInventory(SeatInventoryRecord inv);
    SeatInventoryRecord getInventoryByEvent(Long eventId);
}
