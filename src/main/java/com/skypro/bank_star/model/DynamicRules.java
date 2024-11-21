package com.skypro.bank_star.model;

import org.springframework.data.jdbc.repository.query.Query;

import java.util.List;
import java.util.UUID;

public class DynamicRules {
    private Long id;
    private Long userId;
    private List<Query> queries;

    public DynamicRules(Long id, Long userId, List<Query> queries) {
        this.id = id;
        this.userId = userId;
        this.queries = queries;
    }

    public DynamicRules() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }
}