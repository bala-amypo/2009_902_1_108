package com.example.demo.service.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import com.example.demo.service.EventRecordService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventRecordServiceImpl implements EventRecordService {

    private final EventRecordRepository eventRepo;

    public EventRecordServiceImpl(EventRecordRepository eventRepo) {
        this.eventRepo = eventRepo;
    }

    @Override
    public EventRecord createEvent(EventRecord event) {
        if (event.getActive() == null) event.setActive(true);
        return eventRepo.save(event);
    }

    @Override
    public EventRecord getEventById(Long id) {
        return eventRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    @Override
    public List<EventRecord> getAllEvents() {
        return eventRepo.findAll();
    }

    @Override
    public EventRecord updateEvent(Long id, EventRecord event) {
        EventRecord existing = getEventById(id);
        existing.setEventName(event.getEventName());
        existing.setEventDate(event.getEventDate());
        existing.setVenue(event.getVenue());
        existing.setBasePrice(event.getBasePrice());
        existing.setActive(event.getActive());
        return eventRepo.save(existing);
    }

    @Override
    public void deleteEvent(Long id) {
        EventRecord existing = getEventById(id);
        eventRepo.delete(existing);
    }

    @Override
    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord event = getEventById(id);
        event.setActive(active);
        return eventRepo.save(event);
    }

    @Override
    public Optional<EventRecord> getEventByCode(String eventCode) {
        return eventRepo.findByEventCode(eventCode);
    }
}
