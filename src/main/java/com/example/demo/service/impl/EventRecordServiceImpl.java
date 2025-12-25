package com.example.demo.service.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventRecordServiceImpl implements EventRecordService {

    private final EventRecordRepository repo;

    public EventRecordServiceImpl(EventRecordRepository repo) {
        this.repo = repo;
    }

    @Override
    public EventRecord createEvent(EventRecord event) {
        return repo.save(event);
    }

    @Override
    public EventRecord getEventById(Long id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found: " + id));
    }

    @Override
    public List<EventRecord> getAllEvents() {
        return repo.findAll();
    }

    @Override
    public EventRecord updateEvent(Long id, EventRecord event) {
        EventRecord existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found: " + id));
        existing.setName(event.getName());
        existing.setDate(event.getDate());
        existing.setLocation(event.getLocation());
        return repo.save(existing);
    }

    @Override
    public void deleteEvent(Long id) {
        EventRecord existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found: " + id));
        repo.delete(existing);
    }
}
