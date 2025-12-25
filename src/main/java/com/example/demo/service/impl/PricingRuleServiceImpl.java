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
    public PricingRule createRule(PricingRule rule) {
        if(repo.existsByRuleCode(rule.getRuleCode())) {
            throw new RuntimeException("Rule code already exists");
        }
        return repo.save(rule);
    }

    @Override
    public PricingRule updateRule(Long id, PricingRule rule) {
        PricingRule existing = repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Rule not found"));
        existing.setRuleCode(rule.getRuleCode());
        existing.setActive(rule.isActive());
        existing.setDiscount(rule.getDiscount());
        return repo.save(existing);
    }

    @Override
    public List<PricingRule> getActiveRules() {
        return repo.findByActiveTrue();
    }

    @Override
    public PricingRule getRuleByCode(String code) {
        return repo.findByRuleCode(code);
    }
}
