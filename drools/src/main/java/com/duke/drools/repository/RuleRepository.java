package com.duke.drools.repository;

import com.duke.drools.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RuleRepository extends JpaRepository<Rule,Long> {
    Rule findRuleByRuleKey(String ruleKey);
}
