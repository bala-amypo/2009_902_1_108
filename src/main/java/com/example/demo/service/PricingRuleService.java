package com.example.demo.service;

import com.example.demo.model.PricingRule;
import java.util.List;

public interface PricingRuleService {
    List<PricingRule> getAllRules();
    List<PricingRule> getActiveRules();
    PricingRule createRule(PricingRule rule);
    PricingRule getRuleByCode(String code);
    PricingRule updateRule(Long id, PricingRule rule);
}
