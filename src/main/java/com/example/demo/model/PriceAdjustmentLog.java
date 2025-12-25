package com.example.demo.model;

import java.time.LocalDateTime;

public class PriceAdjustmentLog {

    private Long eventId;
    private Double oldPrice;
    private Double newPrice;
    private LocalDateTime changedAt;

    public void prePersist() {
        this.changedAt = LocalDateTime.now();
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Double getOldPrice() { return oldPrice; }
    public void setOldPrice(Double oldPrice) { this.oldPrice = oldPrice; }

    public Double getNewPrice() { return newPrice; }
    public void setNewPrice(Double newPrice) { this.newPrice = newPrice; }

    public LocalDateTime getChangedAt() { return changedAt; }
}
