package com.example.demo.service.impl;

import com.example.demo.exception.BadRequestException;
import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;

import java.util.List;

public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository repo;

    public PricingRuleServiceImpl(PricingRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public PricingRule createRule(PricingRule rule) {
        if (rule.getPriceMultiplier() <= 0) throw new BadRequestException("Price multiplier must be > 0");
        if (repo.existsByRuleCode(rule.getRuleCode())) throw new BadRequestException("Rule code exists");
        return repo.save(rule);
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
    public PricingRule updateRule(Long id, PricingRule rule) {
        PricingRule existing = repo.findById(id).orElseThrow(() -> new RuntimeException("Rule not found"));
        existing.setPriceMultiplier(rule.getPriceMultiplier());
        existing.setActive(rule.getActive());
        return repo.save(existing);
    }

    @Override
    public PricingRule getRuleByCode(String code) {
        return repo.findByRuleCode(code).orElseThrow(() -> new RuntimeException("Rule not found"));
    }
}
