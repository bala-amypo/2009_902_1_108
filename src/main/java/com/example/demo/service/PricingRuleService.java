package com.example.demo.service;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingRuleService {

    @Autowired
    private PricingRuleRepository repo;

    public PricingRule addRule(PricingRule rule) {
        return repo.save(rule);
    }

    public List<PricingRule> getRulesForEvent(Long eventId) {
        return repo.findByEventId(eventId);
    }
}
