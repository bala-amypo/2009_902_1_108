package com.example.demo.service.impl;

import com.example.demo.model.PricingRule;
import com.example.demo.repository.PricingRuleRepository;
import com.example.demo.service.PricingRuleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PricingRuleServiceImpl implements PricingRuleService {

    private final PricingRuleRepository repo;

    public PricingRuleServiceImpl(PricingRuleRepository repo) {
        this.repo = repo;
    }

    @Override
    public PricingRule createRule(PricingRule rule) {
        if (repo.existsByRuleCode(rule.getRuleCode())) {
            throw new RuntimeException("Rule code already exists");
        }
        return repo.save(rule);
    }

    @Override
    public PricingRule updateRule(Long id, PricingRule updatedRule) {
        PricingRule rule = repo.findById(id).orElseThrow(() -> new RuntimeException("Rule not found"));
        rule.setRuleName(updatedRule.getRuleName());
        rule.setActive(updatedRule.isActive());
        return repo.save(rule);
    }

    @Override
    public PricingRule getRuleByCode(String ruleCode) {
        return repo.findByRuleCode(ruleCode);
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return repo.findByActiveTrue().stream().toList();
    }
}
