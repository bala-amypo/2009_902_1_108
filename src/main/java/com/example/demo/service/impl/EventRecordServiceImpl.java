package com.example.demo.service.impl;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EventRecordServiceImpl implements EventRecordService {

    private final List<EventRecord> events = new ArrayList<>();
    private long nextId = 1L;

    @Override
    public EventRecord createEvent(EventRecord event) {
        event.setId(nextId++);
        if (event.getActive() == null) {
            event.setActive(true);
        }
        events.add(event);
        return event;
    }

    @Override
    public EventRecord getEventById(Long id) {
        return events.stream()
                .filter(e -> e.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Event not found with id: " + id));
    }

    @Override
    public List<EventRecord> getAllEvents() {
        return new ArrayList<>(events);
    }

    @Override
    public EventRecord updateEvent(Long id, EventRecord event) {
        EventRecord existing = getEventById(id);
        existing.setEventName(event.getEventName());
        existing.setEventDate(event.getEventDate());
        existing.setVenue(event.getVenue());
        existing.setBasePrice(event.getBasePrice());
        existing.setActive(event.getActive());
        return existing;
    }

    @Override
    public void deleteEvent(Long id) {
        EventRecord existing = getEventById(id);
        events.remove(existing);
    }

    @Override
    public EventRecord updateEventStatus(Long id, boolean active) {
        EventRecord event = getEventById(id);
        event.setActive(active);
        return event;
    }

    @Override
    public Optional<EventRecord> getEventByCode(String eventCode) {
        return events.stream()
                .filter(e -> e.getEventCode().equals(eventCode))
                .findFirst();
    }
}
