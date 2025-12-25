package com.example.demo.repository;

import com.example.demo.model.PricingRule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {
    PricingRule findByRuleCode(String ruleCode);        // for findByRuleCode
    boolean existsByRuleCode(String ruleCode);          // for existsByRuleCode
    Optional<PricingRule> findByActiveTrue();           // for findByActiveTrue
}
