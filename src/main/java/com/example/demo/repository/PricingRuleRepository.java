package com.example.demo.repository;

import com.example.demo.model.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    PricingRule findByRuleCode(String ruleCode);
    // You can define custom queries if needed, e.g.:
    // @Query("SELECT r FROM PricingRule r WHERE r.active = true")
    // PricingRule getActive();
}
