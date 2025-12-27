package com.example.demo.repository;

import com.example.demo.model.PricingRule;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PricingRuleRepository {
    
    private final Map<Long, PricingRule> rules = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    public PricingRule save(PricingRule rule) {
        if (rule.getId() == null) {
            rule.setId(idCounter.getAndIncrement());
        }
        rules.put(rule.getId(), rule);
        return rule;
    }
    
    public boolean existsByRuleCode(String ruleCode) {
        return rules.values().stream()
                .anyMatch(r -> r.getRuleCode().equals(ruleCode));
    }
    
    public List<PricingRule> findAll() {
        return new ArrayList<>(rules.values());
    }
    
    public List<PricingRule> findByActiveTrue() {
        return rules.values().stream()
                .filter(PricingRule::getActive)
                .collect(Collectors.toList());
    }
}