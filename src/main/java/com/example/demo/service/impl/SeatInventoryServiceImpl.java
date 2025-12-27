package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {
    
    private final SeatInventoryRecordRepository seatInventoryRecordRepository;
    private final EventRecordRepository eventRecordRepository;
    
    public SeatInventoryServiceImpl(SeatInventoryRecordRepository seatInventoryRecordRepository, 
                                   EventRecordRepository eventRecordRepository) {
        this.seatInventoryRecordRepository = seatInventoryRecordRepository;
        this.eventRecordRepository = eventRecordRepository;
    }
    
    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord inventory) {
        eventRecordRepository.findById(inventory.getEventId())
                .orElseThrow(() -> new BadRequestException("Event not found"));
        
        if (inventory.getRemainingSeats() > inventory.getTotalSeats()) {
            throw new BadRequestException("Remaining seats cannot exceed total seats");
        }
        return seatInventoryRecordRepository.save(inventory);
    }
    
    @Override
    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return seatInventoryRecordRepository.findByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
    }
}