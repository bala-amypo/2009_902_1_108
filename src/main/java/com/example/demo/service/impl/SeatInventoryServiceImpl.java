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
    public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
        List<SeatInventoryRecord> records = seatRepo.findByEventId(eventId);
        if (records.isEmpty()) {
            throw new RuntimeException("No seat inventory found for eventId: " + eventId);
        }
        SeatInventoryRecord record = records.get(0);
        record.setRemainingSeats(remainingSeats);
        return seatRepo.save(record);
    }

    @Override
    public List<SeatInventoryRecord> getAllInventories() {
        return seatRepo.findAll();
    }

    @Override
    public List<SeatInventoryRecord> getInventoryByEvent(Long eventId) {
        return seatRepo.findByEventId(eventId);
    }
}
