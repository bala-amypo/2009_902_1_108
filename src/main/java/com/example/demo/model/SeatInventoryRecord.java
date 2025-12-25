package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seat_inventory")
public class SeatInventoryRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "event_id", nullable = false)
    private EventRecord event;

    private String seatNumber;
    private Boolean available = true;
    private LocalDateTime bookedAt;
    private Integer remainingSeats;

    public SeatInventoryRecord() {}

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public EventRecord getEvent() { return event; }
    public void setEvent(EventRecord event) { this.event = event; }

    public String getSeatNumber() { return seatNumber; }
    public void setSeatNumber(String seatNumber) { this.seatNumber = seatNumber; }

    public Boolean getAvailable() { return available; }
    public void setAvailable(Boolean available) { this.available = available; }

    public LocalDateTime getBookedAt() { return bookedAt; }
    public void setBookedAt(LocalDateTime bookedAt) { this.bookedAt = bookedAt; }

    public Integer getRemainingSeats() { return remainingSeats; }
    public void setRemainingSeats(Integer remainingSeats) { this.remainingSeats = remainingSeats; }

    // Added for DynamicPricingEngineServiceImpl
    public Double getBasePrice() {
        return event != null ? event.getBasePrice() : 0;
    }

    @PrePersist
    public void prePersist() {
        if (available == null) available = true;
    }
}
