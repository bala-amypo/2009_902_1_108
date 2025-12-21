package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pricing-rules")
public class PricingRuleController {

    private final PricingRuleService ruleService;

    public PricingRuleController(PricingRuleService ruleService) {
        this.ruleService = ruleService;
    }

    // Create rule
    @PostMapping
    public PricingRule createRule(@RequestBody PricingRule rule) {
        return ruleService.createRule(rule);
    }

    // Update rule
    @PutMapping("/{id}")
    public PricingRule updateRule(@PathVariable Long id, @RequestBody PricingRule rule) {
        return ruleService.updateRule(id, rule);
    }

    // Get active rules
    @GetMapping("/active")
    public List<PricingRule> getActiveRules() {
        return ruleService.getActiveRules();
    }

    // Get rule by code
    @GetMapping("/lookup/{ruleCode}")
    public PricingRule getRuleByCode(@PathVariable String ruleCode) {
        return ruleService.getRuleByCode(ruleCode)
                .orElseThrow(() -> new RuntimeException("Rule not found with code: " + ruleCode));
    }

    // Get all rules
    @GetMapping
    public List<PricingRule> getAllRules() {
        return ruleService.getAllRules();
    }
}
