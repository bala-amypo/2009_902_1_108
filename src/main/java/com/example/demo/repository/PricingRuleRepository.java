public interface PricingRuleRepository
        extends JpaRepository<PricingRule, Long> {

    PricingRule findByRuleCode(String ruleCode);

    List<PricingRule> findByActiveTrue();
}
