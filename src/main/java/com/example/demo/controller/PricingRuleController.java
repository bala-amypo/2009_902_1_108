package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/rules")
public class PricingRuleController {

    private final PricingRuleService ruleService;

    public PricingRuleController(PricingRuleService ruleService) {
        this.ruleService = ruleService;
    }

    @PostMapping("/create")
    public PricingRule createRule(@RequestBody PricingRule rule) {
        return ruleService.createRule(rule);
    }

    @GetMapping("/all")
    public List<PricingRule> getAllRules() {
        return ruleService.getAllRules();
    }

    @GetMapping("/active")
    public List<PricingRule> getActiveRules() {
        return ruleService.getActiveRules();
    }

    @PutMapping("/update/{id}")
    public PricingRule updateRule(@PathVariable Long id, @RequestBody PricingRule rule) {
        return ruleService.updateRule(id, rule); // fixed
    }

    @GetMapping("/{code}")
    public PricingRule getRuleByCode(@PathVariable String code) {
        return ruleService.getRuleByCode(code); // fixed
    }
}
