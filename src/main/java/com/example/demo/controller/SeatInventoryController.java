package com.example.demo.controller;

import com.example.demo.model.SeatInventoryRecord;
import com.example.demo.service.SeatInventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/seats")
public class SeatInventoryController {

    @Autowired
    private SeatInventoryService seatInventoryService;

    @PostMapping
    public SeatInventoryRecord create(@RequestBody SeatInventoryRecord seat) {
        return seatInventoryService.create(seat);
    }
}
