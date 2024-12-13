package com.skypro.bank_star.dto;

import com.skypro.bank_star.request.RulesQuery;

import java.util.List;
import java.util.UUID;

public class DynamicRulesDto {
    private UUID id;
    private RulesQuery query;
    private List<String> arguments;
    private boolean negate;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public RulesQuery getQuery() {
        return query;
    }

    public void setQuery(RulesQuery query) {
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