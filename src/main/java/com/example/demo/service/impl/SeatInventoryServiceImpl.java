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
    public SeatInventoryRecord updateRemainingSeats(Long id, Integer seats) {
        SeatInventoryRecord record = seatRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat record not found"));
        record.setRemainingSeats(seats);
        return seatRepo.save(record);
    }

    @Override
    public List<SeatInventoryRecord> getAllInventories() {
        return seatRepo.findAll();
    }

    @Override
    public List<SeatInventoryRecord> getSeatsByEventId(Long eventId) {
        return seatRepo.findByEventId(eventId);
    }
}
