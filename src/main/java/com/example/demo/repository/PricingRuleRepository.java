package com.example.demo.repository;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleRepository {
    boolean existsByRuleCode(String code);
    PricingRule save(PricingRule rule);
    List<PricingRule> findAll();
    List<PricingRule> findByActiveTrue();
}
