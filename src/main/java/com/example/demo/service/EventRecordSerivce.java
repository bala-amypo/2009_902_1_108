package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.EventRecord;

import java.util.List;
import java.util.Optional;

public interface EventRecordService {

    EventRecord createEvent(EventRecord event) throws BadRequestException;

    EventRecord getEventById(Long id) throws ResourceNotFoundException;

    Optional<EventRecord> getEventByCode(String eventCode);

    List<EventRecord> getAllEvents();

    EventRecord updateEventStatus(Long id, boolean active) throws ResourceNotFoundException;
}
