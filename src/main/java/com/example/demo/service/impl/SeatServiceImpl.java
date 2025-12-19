package com.example.demo.service.impl;

import com.example.demo.model.Seat;
import com.example.demo.repository.SeatRepository;
import com.example.demo.service.SeatService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServiceImpl implements SeatService {

    private final SeatRepository repo;

    public SeatServiceImpl(SeatRepository repo) {
        this.repo = repo;
    }

    @Override
    public Seat addSeat(Seat seat) {
        return repo.save(seat);
    }

    @Override
    public Seat updateSeat(Long id, Seat updated) {
        Seat existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));

        existing.setSection(updated.getSection());
        existing.setPrice(updated.getPrice());
        existing.setAvailable(updated.isAvailable());

        return repo.save(existing);
    }

    @Override
    public List<Seat> getSeats() {
        return repo.findAll();
    }

    @Override
    public Seat getSeat(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Seat not found"));
    }

    @Override
    public void deleteSeat(Long id) {
        repo.deleteById(id);
    }
}
