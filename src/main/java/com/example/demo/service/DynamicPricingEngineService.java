package com.example.demo.service;

import com.example.demo.model.DynamicPriceRecord;

public interface DynamicPricingEngineService {
    DynamicPriceRecord getLatestPrice(Long eventId);
}
