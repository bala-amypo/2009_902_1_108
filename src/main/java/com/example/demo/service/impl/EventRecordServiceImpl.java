package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import java.util.List;
import java.util.Optional;

public class EventRecordServiceImpl implements EventRecordService {
    private final EventRecordRepository repo;

    public EventRecordServiceImpl(EventRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public EventRecord createEvent(EventRecord event) {
        if (event.getBasePrice() == null || event.getBasePrice() <= 0) {
            throw new BadRequestException("Base price must be > 0");
        }
        if (repo.existsByEventCode(event.getEventCode())) {
            throw new BadRequestException("Event code already exists");
        }
        return repo.save(event);
    }

    @Override
    public EventRecord getEventById(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Override
    public List<EventRecord> getAllEvents() {
        return repo.findAll();
    }

    @Override
    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord e = repo.findById(id).orElseThrow(() -> new RuntimeException("Event not found"));
        e.setActive(active);
        return repo.save(e);
    }

    @Override
    public Optional<EventRecord> getEventByCode(String code) {
        return repo.findByEventCode(code);
    }
}
