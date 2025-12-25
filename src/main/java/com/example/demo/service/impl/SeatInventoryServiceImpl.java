package com.example.demo.service.impl;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.SeatInventoryRecordRepository;
import com.example.demo.service.SeatInventoryService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatInventoryServiceImpl implements SeatInventoryService {

    private final SeatInventoryRecordRepository seatRepo;

    public SeatInventoryServiceImpl(SeatInventoryRecordRepository seatRepo) {
        this.seatRepo = seatRepo;
    }

    @Override
    public SeatInventoryRecord getInventoryByEvent(Long eventId) {
        return seatRepo.findFirstByEventId(eventId)
                .orElseThrow(() -> new RuntimeException("Inventory not found for event: " + eventId));
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord record) {
        if (record.getAvailable() == null) record.setAvailable(true);
        return seatRepo.save(record);
    }

    @Override
    public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
        SeatInventoryRecord seat = getInventoryByEvent(eventId);
        seat.setRemainingSeats(remainingSeats);
        return seatRepo.save(seat);
    }

    @Override
    public List<SeatInventoryRecord> getAllInventories() {
        return seatRepo.findAll();
    }
}
