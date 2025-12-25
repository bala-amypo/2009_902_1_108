package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository repo;

    public PricingRuleServiceImpl(PricingRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public List<PricingRule> getAllRules() {
        return repo.findAll();
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return repo.findByActiveTrue();
    }

    @Override
    public PricingRule createRule(PricingRule rule) {
        return repo.save(rule);
    }

    @Override
    public PricingRule getRuleByCode(String code) {
        return repo.findByRuleCode(code)
                .orElseThrow(() -> new RuntimeException("Rule not found: " + code));
    }

    @Override
    public PricingRule updateRule(Long id, PricingRule rule) {
        PricingRule existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found: " + id));

        existing.setRuleCode(rule.getRuleCode());
        existing.setMinRemainingSeats(rule.getMinRemainingSeats());
        existing.setMaxRemainingSeats(rule.getMaxRemainingSeats());
        existing.setDaysBeforeEvent(rule.getDaysBeforeEvent());
        existing.setPriceMultiplier(rule.getPriceMultiplier());
        existing.setActive(rule.isActive());

        return repo.save(existing);
    }
}
