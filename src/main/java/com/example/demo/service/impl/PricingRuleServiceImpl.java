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
        List<PricingRule> rules = repo.findByRuleCode(code);
        if (rules.isEmpty()) {
            throw new RuntimeException("Rule not found: " + code);
        }
        return rules.get(0);
    }

    @Override
    public PricingRule updateRule(Long id, PricingRule rule) {
        PricingRule existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found: " + id));
        existing.setDiscount(rule.getDiscount());
        existing.setActive(rule.isActive());
        existing.setRuleCode(rule.getRuleCode());
        return repo.save(existing);
    }
}
