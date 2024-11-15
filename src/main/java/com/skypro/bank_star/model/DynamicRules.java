package com.skypro.bank_star.model;

import org.springframework.data.jdbc.repository.query.Query;

import java.util.List;

public class DynamicRules {
    private Long id;
    private List<Query> queries;

    public DynamicRules(Long id, List<Query> queries) {
        this.id = id;
        this.queries = queries;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }
}