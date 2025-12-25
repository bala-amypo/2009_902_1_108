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
        List<SeatInventoryRecord> seats = seatRepo.findByEventIdList(eventId);
        if (seats.isEmpty()) {
            throw new RuntimeException("Event not found: " + eventId);
        }
        return seats.get(0); // return first match
    }

    @Override
    public SeatInventoryRecord createInventory(SeatInventoryRecord record) {
        return seatRepo.save(record);
    }

    @Override
    public SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) {
        List<SeatInventoryRecord> seats = seatRepo.findByEventIdList(eventId);
        if (seats.isEmpty()) {
            throw new RuntimeException("Event not found: " + eventId);
        }
        SeatInventoryRecord seat = seats.get(0);
        seat.setRemainingSeats(remainingSeats);
        return seatRepo.save(seat);
    }

    @Override
    public List<SeatInventoryRecord> getAllInventories() {
        return seatRepo.findAll();
    }
}
