package com.example.demo.service;

import com.example.demo.exception.BadRequestException;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.model.SeatInventoryRecord;

import java.util.List;
import java.util.Optional;

public interface SeatInventoryService {

    SeatInventoryRecord createInventory(SeatInventoryRecord inventory) throws BadRequestException;

    SeatInventoryRecord updateRemainingSeats(Long eventId, Integer remainingSeats) throws BadRequestException;

    Optional<SeatInventoryRecord> getInventoryByEvent(Long eventId);

    List<SeatInventoryRecord> getAllInventories();
}
