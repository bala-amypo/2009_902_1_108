package com.example.demo.repository.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Repository
public class PricingRuleRepositoryImpl implements PricingRuleRepository {
    
    private final Map<Long, PricingRule> rules = new HashMap<>();
    private final AtomicLong idCounter = new AtomicLong(1);
    
    @Override
    public PricingRule save(PricingRule rule) {
        if (rule.getId() == null) {
            rule.setId(idCounter.getAndIncrement());
        }
        rules.put(rule.getId(), rule);
        return rule;
    }
    
    @Override
    public boolean existsByRuleCode(String ruleCode) {
        return rules.values().stream()
                .anyMatch(r -> r.getRuleCode().equals(ruleCode));
    }
    
    @Override
    public List<PricingRule> findAll() {
        return new ArrayList<>(rules.values());
    }
    
    @Override
    public List<PricingRule> findByActiveTrue() {
        return rules.values().stream()
                .filter(PricingRule::getActive)
                .collect(Collectors.toList());
    }
}