package com.example.demo.repository;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleRepository {
    PricingRule save(PricingRule rule);
    boolean existsByRuleCode(String ruleCode);
    List<PricingRule> findAll();
    List<PricingRule> findByActiveTrue();
}