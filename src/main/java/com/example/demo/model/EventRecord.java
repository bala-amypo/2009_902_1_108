package com.example.demo.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "event_records")
public class EventRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String eventCode;

    @Column(nullable = false)
    private String eventName;

    private String venue;

    private LocalDate eventDate;

    @Column(nullable = false)
    private Double basePrice;

    private LocalDateTime createdAt;

    private Boolean active = true;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<SeatInventoryRecord> inventories;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<DynamicPriceRecord> dynamicPrices;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL)
    private List<PriceAdjustmentLog> adjustmentLogs;

    public EventRecord() {}

    public EventRecord(String eventCode, String eventName, String venue, LocalDate eventDate, Double basePrice, Boolean active) {
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
        this.basePrice = basePrice;
        this.active = active;
    }

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEventCode() { return eventCode; }
    public void setEventCode(String eventCode) { this.eventCode = eventCode; }

    public String getEventName() { return eventName; }
    public void setEventName(String eventName) { this.eventName = eventName; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public Double getBasePrice() { return basePrice; }
    public void setBasePrice(Double basePrice) { this.basePrice = basePrice; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public Boolean getActive() { return active; }
    public void setActive(Boolean active) { this.active = active; }

    public List<SeatInventoryRecord> getInventories() { return inventories; }
    public void setInventories(List<SeatInventoryRecord> inventories) { this.inventories = inventories; }

    public List<DynamicPriceRecord> getDynamicPrices() { return dynamicPrices; }
    public void setDynamicPrices(List<DynamicPriceRecord> dynamicPrices) { this.dynamicPrices = dynamicPrices; }

    public List<PriceAdjustmentLog> getAdjustmentLogs() { return adjustmentLogs; }
    public void setAdjustmentLogs(List<PriceAdjustmentLog> adjustmentLogs) { this.adjustmentLogs = adjustmentLogs; }
}
