package com.example.demo.service;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.repository.SeatInventoryRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SeatInventoryService {

    @Autowired
    private SeatInventoryRecordRepository repo;

    public SeatInventoryRecord create(SeatInventoryRecord s) {
        return repo.save(s);
    }

    public SeatInventoryRecord updateSeats(Long id, int seatsSold) {

        SeatInventoryRecord rec = repo.findById(id).orElseThrow();

        if (rec.getCurrentAvailable() < seatsSold)
            throw new RuntimeException("Not enough seats available");

        rec.setCurrentAvailable(rec.getCurrentAvailable() - seatsSold);

        rec.setLastUpdated(java.time.LocalDateTime.now());

        return repo.save(rec);
    }
}
