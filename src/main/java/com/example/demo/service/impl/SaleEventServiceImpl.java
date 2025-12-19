package com.example.demo.service.impl;

import com.example.demo.model.SaleEvent;
import com.example.demo.repository.SaleEventRepository;
import com.example.demo.service.SaleEventService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SaleEventServiceImpl implements SaleEventService {

    private final SaleEventRepository repo;

    public SaleEventServiceImpl(SaleEventRepository repo) {
        this.repo = repo;
    }

    @Override
    public SaleEvent createEvent(SaleEvent event) {
        return repo.save(event);
    }

    @Override
    public SaleEvent updateEvent(Long id, SaleEvent updated) {
        SaleEvent existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale event not found"));

        existing.setBasePrice(updated.getBasePrice());
        existing.setRemainingStock(updated.getRemainingStock());
        existing.setDemandFactor(updated.getDemandFactor());
        existing.setSeasonMultiplier(updated.getSeasonMultiplier());
        existing.setStartTime(updated.getStartTime());
        existing.setEndTime(updated.getEndTime());

        return repo.save(existing);
    }

    @Override
    public List<SaleEvent> getAll() {
        return repo.findAll();
    }

    @Override
    public SaleEvent getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Sale event not found"));
    }

    @Override
    public void delete(Long id) {
        repo.deleteById(id);
    }
}
