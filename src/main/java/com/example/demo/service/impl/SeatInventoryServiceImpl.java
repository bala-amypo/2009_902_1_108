package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.model.EventRecord;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.SeatInventoryService;
import java.util.Optional;

public class SeatInventoryServiceImpl implements SeatInventoryService {
    private final SeatInventoryRecordRepository invRepo;
    private final EventRecordRepository eventRepo;

    public SeatInventoryServiceImpl(SeatInventoryRecordRepository invRepo, EventRecordRepository eventRepo) {
        this.invRepo = invRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord inv) {
        EventRecord event = eventRepo.findById(inv.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));
        if (inv.getRemainingSeats() > inv.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        return invRepo.save(inv);
    }

    @Override
    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return invRepo.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }
}
