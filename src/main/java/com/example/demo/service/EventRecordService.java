package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventRecordService {
    
    private final EventRecordRepository eventRecordRepository;
    
    public EventRecordService(EventRecordRepository eventRecordRepository) {
        this.eventRecordRepository = eventRecordRepository;
    }
    
    public EventRecord createEvent(EventRecord event) {
        if (event.getBasePrice() == null || event.getBasePrice() <= 0) {
            throw new BadRequestException("Base price must be > 0");
        }
        
        if (eventRecordRepository.existsByEventCode(event.getEventCode())) {
            throw new BadRequestException("Event code already exists");
        }
        
        return eventRecordRepository.save(event);
    }
    
    public EventRecord getEventById(Long id) {
        return eventRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }
    
    public Optional<EventRecord> getEventByCode(String eventCode) {
        return eventRecordRepository.findByEventCode(eventCode);
    }
    
    public List<EventRecord> getAllEvents() {
        return eventRecordRepository.findAll();
    }
    
    public EventRecord updateEventStatus(Long id, Boolean active) {
        EventRecord event = getEventById(id);
        event.setActive(active);
        return eventRecordRepository.save(event);
    }
}