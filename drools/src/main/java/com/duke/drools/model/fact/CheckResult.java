package com.duke.drools.model.fact;

import java.util.ArrayList;
import java.util.List;

public class CheckResult {
    private List<String>rules=new ArrayList<>();

    public List<String> getRules() {
        return rules;
    }

    public void setRule(String rule) {
        rules.add(rule);
    }
}
