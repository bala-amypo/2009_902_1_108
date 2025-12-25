package com.example.demo.service;

import java.util.List;

public interface DynamicPricingEngineService {
    double computeDynamicPrice(Long seatId);
    List<Double> getPriceHistory(Long seatId);
    List<Double> getAllComputedPrices();
}
