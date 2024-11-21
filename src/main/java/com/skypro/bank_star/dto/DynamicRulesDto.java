package com.skypro.bank_star.dto;

import java.util.List;

public class DynamicRulesDto {
    private Long id;
    private Long userId;
    private List<String> queries; // Используем строки или другой тип для запросов

    public DynamicRulesDto(Long id, Long userId, List<String> queries) {
        this.id = id;
        this.userId = userId;
        this.queries = queries;
    }

    public DynamicRulesDto() {
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

    public List<String> getQueries() {
        return queries;
    }

    public void setQueries(List<String> queries) {
        this.queries = queries;
    }
}
