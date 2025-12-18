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

    private String eventName;

    private String venue;

    private LocalDate eventDate;

    @Column(nullable = false)
    private Double basePrice;

    private Boolean active = true;

    private LocalDateTime createdAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDateTime.now();
        if (active == null) active = true;
    }

    @OneToMany(mappedBy = "eventId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SeatInventoryRecord> inventories;

    @OneToMany(mappedBy = "eventId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DynamicPriceRecord> priceRecords;

    @OneToMany(mappedBy = "eventId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PriceAdjustmentLog> adjustmentLogs;

    public EventRecord() {}

    public EventRecord(Long id, String eventCode, String eventName, String venue,
                       LocalDate eventDate, Double basePrice, Boolean active,
                       LocalDateTime createdAt) {
        this.id = id;
        this.eventCode = eventCode;
        this.eventName = eventName;
        this.venue = venue;
        this.eventDate = eventDate;
        this.basePrice = basePrice;
        this.active = active;
        this.createdAt = createdAt;
    }

    // getters + setters
}
