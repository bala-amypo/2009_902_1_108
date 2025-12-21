package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventRecordController {

    private final EventRecordService eventService;

    public EventRecordController(EventRecordService eventService) {
        this.eventService = eventService;
    }

    // Create event
    @PostMapping
    public EventRecord createEvent(@RequestBody EventRecord event) {
        return eventService.createEvent(event);
    }

    // Get event by ID
    @GetMapping("/{id}")
    public EventRecord getEventById(@PathVariable Long id) {
        return eventService.getEventById(id);
    }

    // Get all events
    @GetMapping
    public List<EventRecord> getAllEvents() {
        return eventService.getAllEvents();
    }

    // Update event status
    @PutMapping("/{id}/status")
    public EventRecord updateEventStatus(@PathVariable Long id, @RequestParam boolean active) {
        return eventService.updateEventStatus(id, active);
    }

    // Lookup by event code
    @GetMapping("/lookup/{eventCode}")
    public EventRecord getEventByCode(@PathVariable String eventCode) {
        return eventService.getEventByCode(eventCode)
                .orElseThrow(() -> new RuntimeException("Event not found with code: " + eventCode));
    }
}
