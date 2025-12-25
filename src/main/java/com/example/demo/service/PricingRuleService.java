package com.example.demo.service;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleService {
    List<PricingRule> getAllRules();
    PricingRule getRuleByCode(String code);
    PricingRule updateRule(Long id, PricingRule rule);
}
