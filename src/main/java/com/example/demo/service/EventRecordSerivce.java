package com.example.demo.service;

import com.example.demo.model.EventRecord;
import com.example.demo.repository.EventRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    @Autowired
    private EventRecordRepository eventRepository;

    public EventRecord create(EventRecord e) {
        return eventRepository.save(e);
    }

    public List<EventRecord> getAll() {
        return eventRepository.findAll();
    }

    public EventRecord get(Long id) {
        return eventRepository.findById(id).orElseThrow();
    }

    public void delete(Long id) {
        eventRepository.deleteById(id);
    }
}
