package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
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
    
    @PostMapping
    public ApiResponse<PricingRule> createRule(@RequestBody PricingRule rule) {
        return ApiResponse.success(ruleService.createRule(rule));
    }
    
    @GetMapping
    public ApiResponse<List<PricingRule>> getAllRules() {
        return ApiResponse.success(ruleService.getAllRules());
    }
    
    @GetMapping("/active")
    public ApiResponse<List<PricingRule>> getActiveRules() {
        return ApiResponse.success(ruleService.getActiveRules());
    }
}