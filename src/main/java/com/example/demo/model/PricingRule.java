package com.example.demo.model;

import jakarta.persistence.*;

@Entity
@Table(name = "pricing_rules")
public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String ruleCode;

    private int minRemainingSeats;
    private int maxRemainingSeats;
    private int daysBeforeEvent;

    private double priceMultiplier; // your existing multiplier
    private boolean active;

    public PricingRule() {}

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRuleCode() {
        return ruleCode;
    }

    public void setRuleCode(String ruleCode) {
        this.ruleCode = ruleCode;
    }

    public int getMinRemainingSeats() {
        return minRemainingSeats;
    }

    public void setMinRemainingSeats(int minRemainingSeats) {
        this.minRemainingSeats = minRemainingSeats;
    }

    public int getMaxRemainingSeats() {
        return maxRemainingSeats;
    }

    public void setMaxRemainingSeats(int maxRemainingSeats) {
        this.maxRemainingSeats = maxRemainingSeats;
    }

    public int getDaysBeforeEvent() {
        return daysBeforeEvent;
    }

    public void setDaysBeforeEvent(int daysBeforeEvent) {
        this.daysBeforeEvent = daysBeforeEvent;
    }

    public double getPriceMultiplier() { // this replaces getDiscount()
        return priceMultiplier;
    }

    public void setPriceMultiplier(double priceMultiplier) {
        this.priceMultiplier = priceMultiplier;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
