package com.example.demo.controller;

import com.example.demo.model.PricingRule;
import com.example.demo.service.PricingRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rules")
public class PricingRuleController {

    @Autowired
    private PricingRuleService service;

    @PostMapping
    public PricingRule addRule(@RequestBody PricingRule rule) {
        return service.addRule(rule);
    }

    @GetMapping("/{eventId}")
    public List<PricingRule> rules(@PathVariable Long eventId) {
        return service.getRulesForEvent(eventId);
    }
}
