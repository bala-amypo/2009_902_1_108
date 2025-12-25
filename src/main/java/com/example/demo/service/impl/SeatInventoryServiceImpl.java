package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;

public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRecordRepository inventoryRepo;
    private final EventRecordRepository eventRepo;

    public SeatInventoryServiceImpl(SeatInventoryRecordRepository inventoryRepo,
                                    EventRecordRepository eventRepo) {
        this.inventoryRepo = inventoryRepo;
        this.eventRepo = eventRepo;
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord record) {
        eventRepo.findById(record.getEventId())
                .orElseThrow(() -> new RuntimeException("Event not found"));

        if (record.getRemainingSeats() > record.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        record.preUpdate();
        return inventoryRepo.save(record);
    }

    @Override
    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return inventoryRepo.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Seat inventory not found"));
    }
}
