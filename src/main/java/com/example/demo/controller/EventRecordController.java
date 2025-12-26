package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.model.EventRecord;
import com.example.demo.service.EventRecordService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/events")
public class EventRecordController {
    
    private final EventRecordService eventRecordService;
    
    public EventRecordController(EventRecordService eventRecordService) {
        this.eventRecordService = eventRecordService;
    }
    
    @PostMapping
    public ApiResponse<EventRecord> createEvent(@RequestBody EventRecord event) {
        return ApiResponse.success(eventRecordService.createEvent(event));
    }
    
    @GetMapping
    public ApiResponse<List<EventRecord>> getAllEvents() {
        return ApiResponse.success(eventRecordService.getAllEvents());
    }
    
    @GetMapping("/{id}")
    public ApiResponse<EventRecord> getEvent(@PathVariable Long id) {
        return ApiResponse.success(eventRecordService.getEventById(id));
    }
    
    @PutMapping("/{id}/status")
    public ApiResponse<EventRecord> updateStatus(@PathVariable Long id, @RequestParam Boolean active) {
        return ApiResponse.success(eventRecordService.updateEventStatus(id, active));
    }
}