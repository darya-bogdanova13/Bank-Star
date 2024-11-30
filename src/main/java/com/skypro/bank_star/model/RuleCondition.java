package com.skypro.bank_star.model;

import java.util.List;

public class RuleCondition {
    private String query;
    private List<String> arguments;
    private boolean negate;

    public RuleCondition(String query, List<String> arguments, boolean negate) {
        this.query = query;
        this.arguments = arguments;
        this.negate = negate;
    }

    public RuleCondition() {
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<String> getArguments() {
        return arguments;
    }

    public void setArguments(List<String> arguments) {
        this.arguments = arguments;
    }

    public boolean isNegate() {
        return negate;
    }
    public void setNegate(boolean negate) {
        this.negate = negate;
    }
}