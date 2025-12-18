package com.example.demo.controller;

import com.example.demo.model.EventRecord;
import com.example.demo.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public EventRecord create(@RequestBody EventRecord e) {
        return eventService.create(e);
    }

    @GetMapping
    public List<EventRecord> all() {
        return eventService.getAll();
    }

    @GetMapping("/{id}")
    public EventRecord get(@PathVariable Long id) {
        return eventService.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        eventService.delete(id);
    }
}
