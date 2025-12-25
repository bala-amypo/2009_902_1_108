package com.example.demo.model;

import java.time.LocalDateTime;

public class DynamicPriceRecord {

    private Long eventId;
    private Double computedPrice;
    private String appliedRuleCodes;
    private LocalDateTime computedAt;

    public void prePersist() {
        this.computedAt = LocalDateTime.now();
    }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Double getComputedPrice() { return computedPrice; }
    public void setComputedPrice(Double computedPrice) { this.computedPrice = computedPrice; }

    public String getAppliedRuleCodes() { return appliedRuleCodes; }
    public void setAppliedRuleCodes(String appliedRuleCodes) { this.appliedRuleCodes = appliedRuleCodes; }

    public LocalDateTime getComputedAt() { return computedAt; }
}
